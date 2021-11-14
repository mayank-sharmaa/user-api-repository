package com.home.assignment.userapi.error;

public enum ErrorCodes {

    INVALID_REQUEST("SC-001", "Invalid Request. Please make sure request is in valid format"),
    INVALID_REQUEST_USER_NAME("SC-002", "Invalid Request.Username is empty or null"),
    INVALID_VALID_TO_DATE_TIME("SC-003", "Invalid Request.ValidTo timestamp must be after ValidTo timestamp or Current Date"),
    INVALID_USERID("SC-004", "Invalid Request.UserId is null"),
    INVALID_ROLEID("SC-005", "Invalid Request.RoleId is null"),
    INVALID_UNITID("SC-006", "Invalid Request.UnitId is null"),
    INVALID_REQUEST_NON_UNIQUE_USER_ROLE("SC-007", "Invalid Request. Combination of user id, role id and unit id already present"),
    INVALID_UPDATE("SC-008", "Invalid Request. Resource to be updated is not found"),

    INTERNAL_ERROR("SC-009", "Internal Server Error."),
    USER_NOT_AUTHORIZED("SC-011", "User is not authorized"),
    METHOD_NOT_ALLOWED("SC-012", "Method is not allowed"),
    NOT_ACCEPTABLE("SC-013", "Not acceptable"),
    UNSUPPORTED_MEDIA_TYPE("SC-014", "Unsupported media type"),
    VERSION_MISMATCH_ERROR("SC-015", "Version does not match with current resource version"),
    VERSION_MANDATORY("SC-016", "Version must be specified for Update and Delete requests"),
    INVALID_TIMESTAMP("SC-017", " Time stamp is not in desired format."),
    INVALID_REQUEST_FOREIGN_KEY_CONSTRAINT_FAILED("SC-018", "Invalid Request. Foreign key constraint failed. Entity must be present in parent table before creating in child class. "),
    NO_ERROR("", "");

    String errorCode;
    String errorMessage;

    private ErrorCodes(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
