package com.vitalidudarenka.data.db.entities

import com.vitalidudarenka.domain.entities.Rate

fun RateDB.transformToDomain(): Rate {
    return Rate(code, 0f)
}

fun Rate.transformToDB(): RateDB {
    return RateDB(name)
}