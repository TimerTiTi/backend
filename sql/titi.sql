create database if not exists titi;

use titi;

DELIMITER //

CREATE FUNCTION generate_unique_hash_code() RETURNS VARCHAR(8)
    DETERMINISTIC
BEGIN
    DECLARE new_hash_code VARCHAR(8);

    REPEAT
        SET new_hash_code = LPAD(CONV(FLOOR(RAND() * 99999999), 10, 36), 8, '0');
    UNTIL NOT EXISTS (SELECT 1 FROM members WHERE hashcode = new_hash_code) END REPEAT;

    RETURN new_hash_code;
END //

DELIMITER ;

CREATE TABLE IF NOT EXISTS accounts
(
    id             BIGINT AUTO_INCREMENT,
    username       VARCHAR(30)                                                          NOT NULL UNIQUE COMMENT '아이디(이메일)',
    password VARCHAR(255) COMMENT '비밀번호',
    authority      ENUM ('MEMBER', 'ADMIN')                                             NOT NULL COMMENT '권한',
    account_status ENUM ('ACTIVATED', 'DEACTIVATED', 'SUSPENDED', 'BLOCKED', 'DELETED') NOT NULL COMMENT '계정 상태',
    created_at     DATETIME(6)                                                          NOT NULL COMMENT '생성 일시',
    updated_at     DATETIME(6)                                                          NOT NULL COMMENT '수정 일시',
    PRIMARY KEY (id)
) COMMENT '계정';

CREATE TABLE IF NOT EXISTS members
(
    id                 BIGINT AUTO_INCREMENT,
    account_id         BIGINT                      NOT NULL COMMENT '계정 PK',
    nickname           VARCHAR(15)                 NOT NULL COMMENT '닉네임',
    hashcode           CHAR(8)                     NOT NULL COMMENT '해시코드',
    membership_type    ENUM ('NORMAL', 'PREMIUM')  NOT NULL COMMENT '멤버십 유형',
    profile_image_name VARCHAR(50)                 NOT NULL COMMENT '프로필 이미지 파일 이름',
    profile_image_id   CHAR(36) UNIQUE             NOT NULL COMMENT '프로필 이미지 파일 ID',
    profile_image_type ENUM ('JPG', 'JPEG', 'PNG') NOT NULL COMMENT '프로필 이미지 파일 유형',
    created_at         DATETIME(6)                 NOT NULL COMMENT '생성 일시',
    updated_at         DATETIME(6)                 NOT NULL COMMENT '수정 일시',
    PRIMARY KEY (id),
    UNIQUE INDEX uix_members_nickname (nickname, hashcode)
) COMMENT '회원';

CREATE TRIGGER generate_hashcode
    BEFORE INSERT
    ON members
    FOR EACH ROW
    SET NEW.hashcode = generate_unique_hash_code();

CREATE TABLE IF NOT EXISTS devices
(
    uuid             BINARY(16)  NOT NULL,
    member_id        BIGINT      NOT NULL COMMENT '회원 PK',
    device_type      VARCHAR(255) COMMENT '기기 유형',
    last_accessed_at DATETIME(6) NOT NULL COMMENT '최근 접속 일시',
    created_at       DATETIME(6) NOT NULL COMMENT '생성 일시',
    updated_at       DATETIME(6) NOT NULL COMMENT '수정 일시',
    PRIMARY KEY (uuid),
    CONSTRAINT fk_devices_member_id FOREIGN KEY (member_id) REFERENCES members (id)
) COMMENT '기기';

CREATE TABLE IF NOT EXISTS oauth2_infos
(
    id            BIGINT AUTO_INCREMENT,
    account_id    BIGINT                   NOT NULL COMMENT '계정 PK',
    provider_id   VARCHAR(100)             NOT NULL COMMENT 'OAuth2 서비스 회원 식별자',
    provider      ENUM ('GOOGLE', 'APPLE') NOT NULL COMMENT 'OAuth2 서비스 제공 업체',
    access_token  VARCHAR(100)             NOT NULL COMMENT 'OAuth2 인증 토큰',
    refresh_token VARCHAR(100)             NOT NULL COMMENT 'OAuth2 인증 갱신 토큰',
    created_at    DATETIME(6)              NOT NULL COMMENT '생성 일시',
    updated_at    DATETIME(6)              NOT NULL COMMENT '수정 일시',
    PRIMARY KEY (id),
    CONSTRAINT fk_oauth2_infos_account_id FOREIGN KEY (account_id) REFERENCES accounts (id),
    UNIQUE INDEX uix_oauth2_infos_provider_id (provider, provider_id)
) COMMENT 'OAuth2 정보';

CREATE TABLE IF NOT EXISTS notifications
(
    uid                   BIGINT                                                        NOT NULL,
    notification_category ENUM ('INFORMATION', 'NOTICE', 'MARKETING', 'AUTHENTICATION') NOT NULL COMMENT '알림 종류',
    notification_type     ENUM ('EMAIL')                                                NOT NULL COMMENT '알림 유형',
    notification_status   ENUM ('CREATED', 'COMPLETED', 'FAILED')                       NOT NULL COMMENT '알림 상태',
    target_type           ENUM ('EMAIL')                                                NOT NULL COMMENT '수신 유형',
    target_value          VARCHAR(255)                                                  NOT NULL COMMENT '수신자 정보',
    service_name          ENUM ('AUTH')                                                 NOT NULL COMMENT '서비스명',
    service_request_id    VARCHAR(255)                                                  NOT NULL COMMENT '서비스 요청 ID',
    notified_at           DATETIME(6)                                                   NULL COMMENT '발송 일시',
    created_at            DATETIME(6)                                                   NOT NULL COMMENT '생성 일시',
    updated_at            DATETIME(6)                                                   NOT NULL COMMENT '수정 일시',
    PRIMARY KEY (uid)
) COMMENT '알림';

CREATE TABLE IF NOT EXISTS notification_histories
(
    uid             BIGINT      NOT NULL,
    notification_id BIGINT      NOT NULL COMMENT '알림 PK',
    notification_status ENUM ('CREATED', 'COMPLETED', 'FAILED') NOT NULL COMMENT '알림 상태',
    created_at      datetime(6) NOT NULL COMMENT '생성 일시',
    PRIMARY KEY (uid)
) COMMENT '알림 이력';
