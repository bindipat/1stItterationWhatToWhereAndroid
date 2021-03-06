/**
* Documentation Using Swagger UI
* No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
*
* OpenAPI spec version: v1
* 
*
* NOTE: This class is auto generated by the swagger code generator program.
* https://github.com/swagger-api/swagger-codegen.git
* Do not edit the class manually.
*/
package io.swagger.client.models


/**
 * 
 * @param PostId 
 * @param Description 
 * @param ImagePath 
 * @param ShareType 
 * @param LikeCount 
 * @param DislikeCount 
 * @param CreatedDate 
 * @param CreatedBy 
 * @param UserId 
 */
data class DashboardPostModel (
    val PostId: Long? = null,
    val Description: String? = null,
    val ImagePath: String? = null,
    val ShareType: String? = null,
    val LikeCount: Int? = null,
    val DislikeCount: Int? = null,
    val CreatedDate: java.time.LocalDateTime? = null,
    val CreatedBy: Long? = null,
    val UserId: Long? = null
) {

}

