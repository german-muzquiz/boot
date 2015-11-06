INSERT INTO OAUTH_CLIENT_DETAILS
(CLIENT_ID, CLIENT_SECRET, ACCESS_TOKEN_VALIDITY, ADDITIONAL_INFORMATION, AUTHORITIES, AUTHORIZED_GRANT_TYPES, AUTOAPPROVE, REFRESH_TOKEN_VALIDITY, RESOURCE_IDS, SCOPE, WEB_SERVER_REDIRECT_URI)
VALUES ('boot_webapp',
        -- Client password (59d14f01-38da-401c-8014-b6c0356271c8)
        '$2a$10$LCNswZlwBgyQAvNqgj35C.DuFIm4LVghVKkjS.Gyt5o8ukFHEUaMu',
        3600,
        NULL,
        -- Used when securing a url, example: .antMatcher('/data/list').access("#oauth2.clientHasRole('ROLE_TRUSTED_CLIENT')")
        'ROLE_TRUSTED_CLIENT',
        -- OAuth2 grant types enabled for this client. Possible values : password,authorization_code,refresh_token,implicit,client_credentials
        'password,refresh_token',
        NULL,
        3600,
        -- Resource ids
        'api',
        -- Scopes used when securing a url, example: .antMatchers("/**").access("#oauth2.hasScope('read')")
        'read,write',
        NULL);

-- Default user: admin/admin
INSERT INTO USERS (ID, USERNAME, PASSWORD, ENABLED) VALUES ('9a820f3e-1b4a-45aa-b709-cda7957b1a75', 'admin', '$2a$10$D85xZKQe7AKfUWwL4altkOeYMSK/wf/eisYM7oAQoW6c42WCXlZta', 1);

INSERT INTO AUTHORITIES (ID, USERNAME, AUTHORITY) VALUES ('dc93f50a-5ac7-46ae-91cf-b0d3a4d5adc5', 'admin', 'ROLE_ADMIN');