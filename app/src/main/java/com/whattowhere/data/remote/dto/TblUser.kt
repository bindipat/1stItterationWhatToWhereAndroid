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
 * @param UserId 
 * @param UserName 
 * @param Password 
 * @param FirstName 
 * @param LastName 
 * @param Email 
 * @param Mobile 
 * @param Gender 
 * @param ProfileImage 
 * @param LoginType 
 * @param IsMobileVerified 
 * @param IsEmailVerified 
 * @param UserType 
 * @param FirebaseToken 
 * @param IsActive 
 * @param DeviceType 
 */
data class TblUser (
    val UserId: Long? = null,
    val UserName: String? = null,
    val Password: String? = null,
    val FirstName: String? = null,
    val LastName: String? = null,
    val Email: String? = null,
    val Mobile: String? = null,
    val Gender: String? = null,
    val ProfileImage: String? = null,
    val LoginType: String? = null,
    val IsMobileVerified: Boolean? = null,
    val IsEmailVerified: Boolean? = null,
    val UserType: String? = null,
    val FirebaseToken: String? = null,
    val IsActive: Boolean? = null,
    val DeviceType: String? = null
) {

}

