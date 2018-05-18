package com.iambedant.pizzaapp.data

import com.google.gson.annotations.SerializedName
import org.json.JSONArray

data class Pizza(@SerializedName("variants")
                 val variants: Variants)

data class VariantGroupsItem(@SerializedName("group_id")
                             val groupId: String = "",
                             @SerializedName("variations")
                             val variations: List<VariationsItem>,
                             @SerializedName("name")
                             val name: String = "")

data class Variants(@SerializedName("variant_groups")
                    val variantGroups: List<VariantGroupsItem>,
                    @SerializedName("exclude_list")
                    val excludeList: List<List<ExcludeVarient>>)

data class VariationsItem(@SerializedName("default")
                          val default: Int = 0,
                          @SerializedName("price")
                          val price: Int = 0,
                          @SerializedName("name")
                          val name: String = "",
                          @SerializedName("inStock")
                          val inStock: Int = 0,
                          @SerializedName("id")
                          val id: String = "")

data class ExcludeVarient(@SerializedName("group_id")
                          val groupId: String = "",
                          @SerializedName("variation_id")
                          val variationId: String = "")

data class SelectableVariations(val variationsItem: VariationsItem,
                                var isSelected: Boolean,
                                val isExcluded: Boolean)

data class SelectableVariationGroupItems(val groupId: String, val variations: List<SelectableVariations>, val name: String)

data class SelectVariation(val groupId: String, val variationId: String)

