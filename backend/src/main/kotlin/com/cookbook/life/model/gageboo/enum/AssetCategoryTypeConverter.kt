package com.cookbook.life.model.gageboo.enum

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter

/** 카테고리 타입 컨버터 **/
// https://medium.com/@rnrghks09/jpa-entity-enum-converter를-좀-더-유연하게-관리하는-방법-32e952fba28e (개선 필요)
@Converter(autoApply = true)
class AssetCategoryTypeConverter: AttributeConverter<AssetCategoryType, Int> {
    // attribute를 DB에 저장할 값으로 변경
    override fun convertToDatabaseColumn(attribute: AssetCategoryType): Int {
        return attribute.value;
    }

    // DB에 저장된 값을 attribute 로 변경
    override fun convertToEntityAttribute(db: Int): AssetCategoryType {
        return AssetCategoryType.entries.first{it.value == db}
    }

}