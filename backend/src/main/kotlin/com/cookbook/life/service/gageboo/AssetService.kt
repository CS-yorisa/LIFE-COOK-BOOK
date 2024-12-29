package com.cookbook.life.service.gageboo.asset

import com.cookbook.life.model.gageboo.asset.AssetCategory
import com.cookbook.life.model.gageboo.asset.UserAsset
import com.cookbook.life.model.gageboo.enum.AssetCategoryType
import com.cookbook.life.repository.gageboo.asset.UserAssetQdls
import com.cookbook.life.repository.gageboo.asset.AssetRepository
import com.cookbook.life.repository.gageboo.asset.UserAssetRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class AssetService {

    // 따로따로 Bean 등록 대신 Repository 구조 잡기 개선 필요
    // https://jaehoney.tistory.com/230
    // https://jaehoney.tistory.com/211

    @Autowired
    private lateinit var assetRepository: AssetRepository

    @Autowired
    private lateinit var userAssetQdls: UserAssetQdls

    @Autowired
    private lateinit var userAssetRepository: UserAssetRepository

    // 전체 자산 카테고리 조회(메인)
    fun getAssetCommonCategoryList(): List<AssetCategory>{
        return assetRepository.findAll()
    }

    // INSERT는 queryDsl이 훨씬 복잡하므로 기존 JPA 사용
    // 유저별 자산 저장
    fun insertUserAsset(userAsset: UserAsset): Boolean{
//         실제로 존재하는 자산 종류인지 확인
        if(!userAssetQdls.validationAssetNo(userAsset.assetCategoryNo)){
            throw IllegalArgumentException("category no validation fail") // 추후 custom exception 만들기
        }

//         자산번호로 자산 카테고리 타입 조회, ACCOUNT/INVESTMENT 일 경우 이자율을 작성했는지 검증
//         Enum에 converter 사용 시 QueryDsl에서 사용 안 되는 것으로 보임
        var originType: AssetCategoryType? = assetRepository.findAssetCategoryByAssetCategoryNo(userAsset.assetNo)?.categoryType
        if((AssetCategoryType.ACCOUNT.equals(originType) || AssetCategoryType.INVESTMENT.equals(originType)) && userAsset.interestRate == null){
            throw IllegalArgumentException("ACCOUNT or INVESTMENT must have interestRate")
        }

//         카드일 경우 카드 값이 빠져나가는 통장 번호 추가 필요
        if(AssetCategoryType.CARD.equals(originType) && userAsset.withdrawalAssetNo == null){
            throw IllegalArgumentException("CARD must have withdrawalAssetNo")
        }

//         최대 값 확인 후 매핑
        val num:Int = userAssetQdls.findUserAssetMaxNo(userAsset.userId)
        userAsset.assetNo = num

//         저장
        userAssetRepository.save(userAsset)

        return true
    }

    // 유저별 자산 전체 목록 조회
    fun selectUserAsset(userId: UUID): List<UserAsset>{
        return userAssetRepository.findAllByUserId(userId)
    }

    // 유저 자산 삭제 (단건)
    @Transactional
    fun deleteUserAsset(userId:UUID, assetNo:Int):Boolean{
        userAssetRepository.deleteUserAssetByUserIdAndAssetNo(userId, assetNo)
        return true
    }


    // 유저 자산 전체 삭제 (회원 탈퇴용)
    @Transactional
    fun deleteAllUserAsset(userId:UUID):Boolean{
        userAssetRepository.deleteUserAssetByUserId(userId)
        return true
    }
}