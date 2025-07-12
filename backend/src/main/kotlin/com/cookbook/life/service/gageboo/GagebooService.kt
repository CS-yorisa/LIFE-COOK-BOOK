package com.cookbook.life.service.gageboo

import com.cookbook.life.exception.GraphqlErrorCode
import com.cookbook.life.exception.GraphqlException
import com.cookbook.life.model.gageboo.gageboo.Gageboo
import com.cookbook.life.model.gageboo.gageboo.MainCategory
import com.cookbook.life.repository.gageboo.GagebooCategoryQdls
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

    @Autowired
    private lateinit var gagebooCategoryQdls: GagebooCategoryQdls

    /*
        가계부 조회
     */
    fun getGagebooById(memberId : UUID, mainCategory: MainCategory?): List<Gageboo>{
        var spec: Specification<Gageboo?> =
            Specification<Gageboo?> { root: Root<Gageboo?>?, query: CriteriaQuery<*>?, criteriaBuilder: CriteriaBuilder? -> null }


        spec = spec.and(findByMemberId(memberId))

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
            // 가계부 카테고리 타입에 따라 지출 포함 여부 설정
            gageboo.expenceInclude = MainCategory.EXPENSES.equals(gageboo.categoryType)
        }

        // 지출일 경우 음수로 저장, 수입일 경우 양수로 저장
        if((MainCategory.EXPENSES.equals(gageboo.categoryType) && gageboo.amounts.compareTo(BigDecimal.ZERO) == 1)
            || (MainCategory.INCOME.equals(gageboo.categoryType) && gageboo.amounts.compareTo(BigDecimal.ZERO) == -1)) {
            gageboo.amounts = gageboo.amounts.multiply(BigDecimal.valueOf(-1))
        }

        // 존재하는 카테고리 번호인지 확인
        val categoryNo = gagebooCategoryQdls.validationUserCategory(memberId = gageboo.memberId, categoryNo = gageboo.categoryNo, mainCategory = gageboo.categoryType);
        if(categoryNo == null){
            throw GraphqlException(GraphqlErrorCode.CATEGORY_NOT_FOUND)
        }


        var returnGageboo = gagebooRepository.save(gageboo)

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
    fun deleteGageboo(gagebooNo:Int, memberId:UUID): Int{
        return gagebooRepository.deleteByGagebooNoAndMemberId(gagebooNo, memberId)
    }

    /* (회원 탈퇴용) 회원 전체 가계부 삭제 */
    fun deleteUserAllGageboo(memberId: UUID): Int{
        return gagebooRepository.deleteByMemberId(memberId)
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
    fun findByMemberId(memberId: UUID): Specification<Gageboo?> {
        return Specification<Gageboo?> { root: Root<Gageboo?>, query: CriteriaQuery<*>?, criteriaBuilder: CriteriaBuilder ->
            criteriaBuilder.equal(
                root.get<Any>("memberId"),
                memberId
            )
        }
    }
}