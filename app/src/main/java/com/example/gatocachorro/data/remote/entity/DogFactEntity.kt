package com.example.gatocachorro.data.remote.entity

import com.google.gson.annotations.SerializedName

/**
 * Resposta de https://dogapi.dog/api/v2/facts
 * {"data": [{"id": "...", "type": "fact", "attributes": {"body": "..."}}]}
 *
 * O JSON tem objetos aninhados, entao cada nivel vira uma classe.
 */
class DogFactEntity {
    @SerializedName("data")
    var data: List<DogFactDataEntity> = emptyList()
}

class DogFactDataEntity {
    @SerializedName("id")
    var id: String = ""

    @SerializedName("type")
    var type: String = ""

    @SerializedName("attributes")
    var attributes: DogFactAttributesEntity = DogFactAttributesEntity()
}

class DogFactAttributesEntity {
    @SerializedName("body")
    var body: String = ""
}
