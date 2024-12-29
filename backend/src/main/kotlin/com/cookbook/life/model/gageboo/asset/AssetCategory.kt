package com.cookbook.life.model.gageboo.asset

import com.cookbook.life.model.gageboo.enum.AssetCategoryType
import jakarta.persistence.*
import lombok.Getter

/* 자산 메인 카테고리 (고정, 개인이 추가 불가능. 조회용) */
@Entity
@Table(schema = "gageboo")
@Getter
class AssetCategory (
    /* 자산 카테고리 번호 */
    @Id var assetCategoryNo: Int,

    /* 카테고리 타입 */
    // @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    var categoryType: AssetCategoryType,

    /* 카테고리명 */
    @Column(nullable = false)
    var categoryName: String){

}