package com.emotion.api.exception;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE("400", " Invalid Input Value"),
    INVALID_TYPE_VALUE("400", " Invalid Type Value"),
    ENTITY_NOT_FOUND("401", " Entity Not Found"),
    HANDLE_ACCESS_DENIED("403", "Access is Denied"),
    METHOD_NOT_ALLOWED("405", " Invalid Input Value"),
    NULL_VALUE("406", "NULL_VALUE"),
    INTERNAL_SERVER_ERROR("500", "Server Error"),
    UN_AUTHORIZED("501", "AccessToken check"),
    RESOURCE_NOT_FOUND("404", "RESOURCE NOT FOUND"),

    //user
    VALUE_DUPLICATE("1004", "This is a duplicate"),
    EXPIRE_TIME("1005", "Certification has expired."),
    DIFFERENT_NUMBER("1006", "The authentication number is different"),
    USER_NOT_FOUND("1007", "there is no User"),
    USER_ALLREADY_SIGNUP("1008", "USER_ALLREADY_SIGNUP"),
    DO_NOT_TWOROOM_LEADER("1009", "YOU CANNOT BE THE LEADER OF MORE THAN ONE ROOM"),
    DO_NOT_LEADER("1010", "There is no leader who is a leader."),
    DO_NOT_ROOM("1011", "There is no room"),
    ALREADY_JOINED_DEPARTMENT_OR_FRIEND_GROUP("1012", "Already Joined Department or FiendGroup"),
    PASSWORD_IS_DIFFERENT("1013", "Password is different."),
    IMAGE_NOT_FOUND("1014", "there is no IMAGE"),

    //calendar
    CALENDAR_NODATA("1200", "There is no calendar data."),
    FAILED_TO_GENERATE_NIGHT_WORK_SCHEDULE("1201", "Failed to generate night work schedule."),
    FAILED_TO_GENERATE_DAY_WORK_SCHEDULE("1202", "Failed to generate day work schedule."),
    FAILED_TO_GENERATE_EVENING_WORK_SCHEDULE("1203", "Failed to generate evening work schedule."),

    //file
    FILE_NOT_CONVERT("1400", "File converting failed."),

    //workRequest

    WORK_REQUSET_NODATA("1300", "There is no work request."),
    WORK_REQUSET_READ("1301", "There is workRequest Read."),

    //auth
    EMAIL_DUPLICATION("1100", "Email is Duplication"),
    DOMAIN_DUPLICATION("1101", "domain is Duplication"),
    MAIL_NOT_FOUND("1102", "There is no mail.");


    private final String code;
    private final String message;

    ErrorCode(final String code, final String message) {
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }
}