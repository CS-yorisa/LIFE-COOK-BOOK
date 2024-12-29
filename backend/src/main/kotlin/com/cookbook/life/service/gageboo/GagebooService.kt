package com.cookbook.life.service.gageboo

import com.cookbook.life.model.gageboo.gageboo.Gageboo
import com.cookbook.life.model.gageboo.MainCategory
import com.cookbook.life.repository.gageboo.GagebooQdls
import com.cookbook.life.repository.gageboo.GagebooRepository
import com.cookbook.life.repository.gageboo.asset.UserAssetRepository
import jakarta.persistence.EntityManager
import jakarta.persistence.criteria.CriteriaBuilder
import jakarta.persistence.criteria.CriteriaQuery
import jakarta.persistence.criteria.Predicate
import jakarta.persistence.criteria.Root
import lombok.RequiredArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.math.BigDecimal
import java.util.*

@Service
@RequiredArgsConstructor
class GagebooService(private val entityManager: EntityManager){
    @Autowired
    private lateinit var gagebooRepository: GagebooRepository

    @Autowired
    private lateinit var userAssetRepository: UserAssetRepository

    @Autowired
    private lateinit var gagebooQdls: GagebooQdls

    /*
        가계부 조회
     */
    fun getGagebooById(userId : UUID, mainCategory: MainCategory?): List<Gageboo>{
        var spec: Specification<Gageboo?> =
            Specification<Gageboo?> { root: Root<Gageboo?>?, query: CriteriaQuery<*>?, criteriaBuilder: CriteriaBuilder? -> null }


        spec = spec.and(findByUserId(userId))

        // 지출만 확인
        if(MainCategory.EXPENSES.equals(mainCategory)){
            spec = spec.and(findExpences())
        }
        return gagebooRepository.findAll(spec);
    }

    /*
        가계부 저장
     */
    fun saveGageboo(gageboo: Gageboo): Gageboo {

        // 지출 포함 여부가 null 인 경우
        if (gageboo.expenceInclude == null) {
            gageboo.expenceInclude = MainCategory.EXPENSES.equals(gageboo.categoryType)
        }

        // 지출일 경우 음수로 저장, 수입일 경우 양수로 저장
        if((MainCategory.EXPENSES.equals(gageboo.categoryType)
                && gageboo.amounts.compareTo(BigDecimal.ZERO) == 1)
            || (MainCategory.INCOME.equals(gageboo.categoryType)
                && gageboo.amounts.compareTo(BigDecimal.ZERO) == -1)) {

            gageboo.amounts = gageboo.amounts.multiply(BigDecimal.valueOf(-1))
        }

        val gagebooNo: Int = gagebooQdls.findUserGagebooMaxNo(gageboo.userId)
        gageboo.gagebooNo = gagebooNo

        var returnGageboo = gagebooRepository.save(gageboo)


        // 자산에 해당하는 전체 총액 sum => 업데이트를 해준다
        // 1. 집계 테이블을 따로 ⇒ 월별 집계 (best로 보임 => 이걸로 일단 진행해보기로 ~~~)
        // 사용량이 많아지면 문제는된다;; 흑흑.. 잘됐네 ^^ 파티셔닝.... 년도만 파티션먼저 생각좀해보렉요.
        // 2. cache 를 적용한다... 생각좀 해볼게요;


        // 지출 확인 후 자산 총액 변경 (가계부를 계속 수정할 수 있으므로 x)
//        val userAsset = userAssetRepository.findByUserIdAndAssetNo(gageboo.userId, gageboo.assetNo)
//        // dirtychecking
//        userAsset.assetAmount += gageboo.amounts

        return returnGageboo
    }

    /*
        가계부 업데이트
     */
    fun updateGageboo(gageboo: Gageboo): Gageboo {
        return gagebooRepository.save(gageboo)
    }

    /*
        가계부 번호와 유저아이디를 가지고 가계부 삭제
     */
    fun deleteGageboo(gagebooNo:Int, userId:UUID): Int{
        return gagebooRepository.deleteByGagebooNoAndUserId(gagebooNo, userId)
    }

    /* (회원 탈퇴용) 회원 전체 가계부 삭제 */
    fun deleteUserAllGageboo(userId: UUID): Int{
        return gagebooRepository.deleteByUserId(userId)
    }

    /*
        predicate를 통해 지출 목록만 조회
     */
    fun findExpences(): Specification<Gageboo?>? {
        return Specification<Gageboo?> { root: Root<Gageboo?>, query: CriteriaQuery<*>?, criteriaBuilder: CriteriaBuilder ->
            val predicates: MutableList<Predicate> =
                ArrayList()
            predicates.add(criteriaBuilder.equal(root.get<Boolean>("expenceInclude"), true))
            predicates.add(criteriaBuilder.lessThan(root.get<Int>("amounts"), 0))
            criteriaBuilder.and(*predicates.toTypedArray<Predicate>())
        }
    }

    /*
        predicate를 통해 특정 유저의 가계부만 조회
     */
    fun findByUserId(userId: UUID): Specification<Gageboo?> {
        return Specification<Gageboo?> { root: Root<Gageboo?>, query: CriteriaQuery<*>?, criteriaBuilder: CriteriaBuilder ->
            criteriaBuilder.equal(
                root.get<Any>("userId"),
                userId
            )
        }
    }
}