package com.cookbook.life.service.gageboo

import com.cookbook.life.model.gageboo.gageboo.GagebooCategory
import com.cookbook.life.model.gageboo.MainCategory
import com.cookbook.life.repository.gageboo.GagebooCategoryQdls
import com.cookbook.life.repository.gageboo.GagebooCategoryRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.ArrayList
import java.util.UUID

@Service
class GagebooCategoryService {
    @Autowired
    private lateinit var gagebooCategoryRepository: GagebooCategoryRepository

    @Autowired
    private lateinit var gagebooCategoryQdls: GagebooCategoryQdls

    // 기본 카테고리 갯수
    private final val BASIC_CATEGORY_COUNT:Int = 3

    /*
      회원 가입 시 기본 카테고리 생성
     */
    @ExceptionHandler
    fun makeBasicCategory(userId: UUID): Int{
        val gagebooCategoryList: MutableList<GagebooCategory> =
            ArrayList()
        gagebooCategoryList.add(GagebooCategory(0, userId, MainCategory.EXPENSES, "식비"))
        gagebooCategoryList.add(GagebooCategory(1, userId, MainCategory.INCOME, "급여"))
        gagebooCategoryList.add(GagebooCategory(2, userId, MainCategory.TRANSFER, "이체"))
        gagebooCategoryRepository.saveAll(gagebooCategoryList)
        return 1
    }

    /*
      해당 유저가 가지고 있는 카테고리 목록 조회
     */
    fun getGagebooCategoryById(userId: UUID): List<GagebooCategory> {
        return gagebooCategoryRepository.findAllByUserId(userId)
    }

    /*
     유저마다 카테고리 추가
     */
    fun saveGagebooCategory(gagebooCategory: GagebooCategory): GagebooCategory {

        // 최대 값 확인 후 매핑
        val categoryNo: Int = gagebooCategoryQdls.findUserCategoryMaxNo(gagebooCategory.userId)
        gagebooCategory.categoryNo = categoryNo


        return gagebooCategoryRepository.save(gagebooCategory)
    }



    /*
      유저 카테고리 수정
     */
    fun updateGagebooCategory(gagebooCategory: GagebooCategory): GagebooCategory {
        return gagebooCategoryRepository.save(gagebooCategory)
    }

    /*
       유저 카테고리 삭제
     */
    @Transactional
    fun deleteGagebooCategory(userId: UUID, categoryNo: Int):Boolean{
        // 기본 카테고리는 삭제할 수 없음
        if(categoryNo <= BASIC_CATEGORY_COUNT){
            throw IllegalArgumentException("기본 카테고리는 삭제할 수 없음")
        }

        gagebooCategoryRepository.deleteGagebooCategoryByUserIdAndCategoryNo(userId, categoryNo)

        return true
    }

    /*
     유저 카테고리 전체 삭제 (회원 탈퇴용)
     */
    @Transactional
    fun deleteAllUserCategory(userId: UUID): Boolean{
        gagebooCategoryRepository.deleteGagebooCategoriesByUserId(userId)
        return true
    }
}