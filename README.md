[![License](https://img.shields.io/github/license/betler/g-mail-relayer?style=flat-square)](https://www.gnu.org/licenses/gpl-3.0.en.html) [![GitHub Release](https://img.shields.io/github/release/betler/g-mail-relayer.svg?style=flat-square)](https://github.com/betler/g-mail-relayer/releases/latest) ![In development](https://img.shields.io/badge/status-current_development-green?style=flat-square) [![CodeFactor](https://www.codefactor.io/repository/github/betler/g-mail-relayer/badge?style=flat-square)](https://www.codefactor.io/repository/github/betler/g-mail-relayer)
<<<<<<< HEAD

# Table of contents

- [g-mail-relayer](#g-mail-relayer)
- [Features](#features)
  * [Current status](#current-status)
  * [Not supported features](#not-supported-features)
- [REST API Usage](#rest-api-usage)
  * [Extended documentation](#extended-documentation)
  * [Examples](#examples)
  * [/mail/send method (POST)](#-mail-send-method--post-)
    + [EmailMessage](#emailmessage)
    + [Attachment](#attachment)
    + [Header](#header)
    + [SendMailResult](#sendmailresult)
  * [Error handling](#error-handling)
    + [400 error code - validations](#400-error-code---validations)
    + [500 error code - something went wrong](#500-error-code---something-went-wrong)
- [SMTP Relaying](#smtp-relaying)
  * [Sending method selection headers](#sending-method-selection-headers)
- [Configuration](#configuration)
  * [application.properties](#applicationproperties)
  * [json config](#json-config)
  * [Server matching algorithm](#server-matching-algorithm)
  * [I'm confused](#i-m-confused)
  * [Still confused...](#still-confused)
  * [Configuration file rules](#configuration-file-rules)

<small><i><a href='http://ecotrust-canada.github.io/markdown-toc/'>Table of contents generated with markdown-toc</a></i></small>

=======
>>>>>>> branch 'master' of https://github.com/betler/g-mail-relayer.git

# g-mail-relayer

This project develops a Java Spring Boot SMTP relayer and an API to send emails. The need comes due to some limitations found in my projects and I felt like building it myself. I need to talk to old NTLM servers and not all applications can. Also, sending a lot of emails synchronously in my applications is slow, so an async sending method was desirable. Multiple relay servers are also supported and selected depending on the incoming message.

The basics are:
 + A Spring Boot REST API application able to relay emails coming from another application, asynchronously, so the sender can forget about waiting to say EHLO! to a external server.
 + It includes a vanilla SMTP server to cover legacy code that cannot change to call to an API or third-party applications (as an example of my needs, a replacement for MultiSMTP OTRS plugin).
 + The relaying server is selected depending on the parameters of the incoming message and the configuration of the relay servers.

Thanks to [@avthart]( https://github.com/avthart ) and his [spring-boot-james-smtp-server]( https://github.com/avthart/spring-boot-james-smtp-server ) project as it enlightened me in the using of Apache James.

# Features 
This is the list of intended features. Not all of them are implemented, so check below.

1. Email sending/relaying:
    * HTML or text emails
    * Attachments (attached or inline)
    * Specific headers
    * Support for several SMTP destination severs
2. Defer email sending to a specific time
3. Store information about the email
    + Reception time
    + Application sending the email, so it can support different applications
    + Message type (application dependant so the application can search about specific type of mails)
    + Status
    + Last sending time (last trying to send the message)
    + Number of retries
    + to/cc/bc
    + ¿Body of the message? Still thinking about it, maybe gzipped to avoid space usage
4. Support for NTLM authentication
5. Support for several relaying servers
6. Able to send email through a REST API (no auth)
7. Able to relay email through a standard 25-smtp port
8. Able to retry email sending for a specified number of times and specified amount of time.
9. Support for callbacks in message sent or message NOK events
10. Search/statistics methods.
    + Able to search
      + Received in a period of time
      + By message status / type
    + Able to give information about
      + Number of received emails
      + Number of OK/NOK/pending/retrying messages 
11. Separate logs for each application
12. Plugins for beforeSend and afterSend events
13. Hooks for success / error messages
14. API method to fetch status of sent mails
15. Client GUI for the statistics module

## Current status
I'm trying to keep this up to date, but no release is still out, so this refers to the master branch:

| #    | Description                                 | Status                                                       |
| ---- | ------------------------------------------- | ------------------------------------------------------------ |
| 1    | Send emails                                 | ![done](https://img.shields.io/badge/requisite-done!-green)  |
| 2    | Defer email sending                         | ![pending](https://img.shields.io/badge/requisite-pending-red) |
| 3    | Store information about the email           | ![pending](https://img.shields.io/badge/requisite-pending-red) |
| 4    | Support for NTLM authentication             | ![done](https://img.shields.io/badge/requisite-done!-green)  |
| 5    | Support for several relaying servers        | ![done](https://img.shields.io/badge/requisite-done!-green)  |
| 6    | REST API                                    | ![done](https://img.shields.io/badge/requisite-done!-green)  |
| 7    | Standard 25-smtp port                       | ![done](https://img.shields.io/badge/requisite-done!-green)  |
| 8    | Retry email sending                         | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 9    | Support for callbacks                       | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 10   | Search/statistics methods                   | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 11   | Separate logs for each application          | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 12   | Plugins for beforeSend and afterSend events | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 13   | Hooks for success / error messages          | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 14   | API method to fetch status of sent mails    | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 15   | Client GUI for the statistics module        | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive) |

## Not supported features

This list includes the features that have been thought of and have been rejected:

+ HTTPS: As this is intented for internal use an not to be exposed, don't think this feature is needed. Anyway, it's very simple to configure an Apache/Nginx/whatever-the-server-you-like in front of the application.
+ API authentication: Same reason. This is intended for internal use. Anyway, a proxy with basic auth could be set in front of the api or more professional applications like WSO2 API Manager.

# REST API Usage

## Extended documentation

Further documentation can be found in:

* [Swagger.io](https://app.swaggerhub.com/apis/c-vitae.pro/g-mail-relayer-api/1.0.0#/models)
* [api.json file](https://github.com/betler/g-mail-relayer/blob/master/src/main/resources/api/api.json)
* [Locally generated HTML doc](https://github.com/betler/g-mail-relayer/blob/master/src/main/resources/api/api-doc.html)

## Examples

Examples of api calls can be found in [samples directory](https://github.com/betler/g-mail-relayer/tree/master/src/main/resources/samples/api).

## /mail/send method (POST)

The only existing method is `POST` `/mail/send`. It receives a `EmailMessage` json object and sends an email with the matching configuration for that email.

### EmailMessage
This is the json object representing an email message that is going to be sent:

| Property       | Description                              |
|----------------|------------------------------------------|
| applicationId  | Optional ID to identify the calling application with two objectives. Separate sending configuration can be placed in function of this parameter and it is (will be) used in statistics. No specific format required. Example: `APP1`|
| messageType | Optional message type identification. This property can't be set without `applicationId` and refers to a specific mail type for a specific app. Separate sending configuration can be placed in function of this parameter and it is (will be) used in statistics. No specific format required. Example: `Password recovery`. |
| from | Email address specifying the sender of the message. If no `applicationId` is set, this field could be used to select the sending configuration. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. Keep in mind that this address, although mandatory, can be overridden before sending/relaying the email. See sending configuration chapter for more info. Although is technically optional, as it can be overridden by configuration, that check cannot be made on mail parsing. This could lead a situation in which a from address is not set and the address is not overridden in the matched configuration, and only in the case of `PRIORITY_SYNC` sending method could the error be raised before the sending. To avoid this, this field is mandatory. |
| replyTo | Optional field to set the 'reply to' address. This field is not overridden in any case. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. |
| to | Array of destination addresses. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. |
| cc | Optional array of cc addresses. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. |
| bcc | Optional array of bcc addresses. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. |
| subject | Subject of the message. Maximum length is 255, not only because that is the max length of a subject in MS Outlook, but because... hey, who wants to read an email with such a long subject, anyway? Not me. |
| body | Body of the message. It can be text or html. Format must be specified with the `textFormat` field, and encoding must be specified with the `textEncoding` field. Max length is 50.000, but still thinking if it should be greater or set by configuration. |
| textFormat | Specifies if the body is text or HTML. Possible values are `TEXT` and `HTML`. |
| textEncoding | Encoding of the message subject, body and addresses. Almost any field in this object may be affected by this setting. I know this is not very clear. |
| priority | Sets the priority of the message. Values 1 to 5, from the highest[1] to lowest[5]. This value is set in an `X-Priority` header. No other headers like `X-MSMail-Priority` or `Importance` are set. Anyway, if you need any of these, they can be set in the `headers` field. |
| notBefore | Optionally delay message delivery until the time specified, as defined by [date-time - RFC3339](http://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14 "date-time - RFC3339"). It is not guaranteed that the email will be sent exactly at this time, but at the first scheduled delivery time after this time. This option is ignored if `deliveryType` is set to other than `QUEUE`. Example: '2015-03-17T10:30:45Z' |
| deliveryType | Sets the delivery type: <ul><li>`PRIORITY_SYNC` makes a synchronized immediate sending of the message. The API does not return until the message is delivered (or tried to).</li><li>`PRIORITY_ASYNC` makes an immediate background sending. The API returns the ID of the message with `QUEUED` status but not the _"Message-ID"_ header. The message is sent immediately in the background.</li><li>`QUEUE` queues the message until the next scheduled batch processing of queued mails'</li></ul> |
| attachments | Array of attachments. Object is explained below. |
| headers | Array of headers to added to the message. Object is explained below. |

### Attachment
This is the json object representing an attachment included in the message.

| Property       | Description                              |
|----------------|------------------------------------------|
| cid            | Optional CID identificator for inline attachements. Setting a CID will force the attachment to be inlined. If you do not want the attachment to be inlined, do not set the CID field. Alphanumeric characters and `._@-$&()[]` are allowed |
| filename | Filename of the attachment. Alphanumeric characters, space and `_-$&()[]` are allowed. |
| contentType | Optional content type. It must include the encoding for text files. Valid values:<ul><li>`"contentType" : "text/plain; charset=UTF-8"`</li><li>`"contentType" : "image/png",`</li></ul> |
| content | The file contents in Base64 representation. Current max size is 6MB. |

### Header
This is the json object representing a header included in the message.

| Property       | Description                              |
|----------------|------------------------------------------|
| name | Name of the header. |
| value | Value of the header. |

This one wasn't tough, was it?

### SendMailResult

When a sending operation is successful, the following information is returned. Keep in mind that this doesn't mean that the email is already sent:

| Property  | Description                                                  |
| --------- | ------------------------------------------------------------ |
| status    | Status of the message. Possible values: <ul><li>`QUEUED` means the message is stored in DB and queued for sending. This could happen with emails that have been marked with a sending type of `QUEUE`.</li><li>`SENDING` means the message is being sent asynchronously. This happens when the sending type is `PRIORITY_ASYNC`</li><li>`SENT` means the message is already sent. this happens when the sending type is `PRIORITY_SYNC`</li></ul> |
| id        | Internal ID given by the application to the message. This could be used to further query of the status of the message, if at anytime that is implemented. |
| messageId | The value of the _"Message-ID"_ header. This value is only filled for `PRIORITY_SYNC` sending. |
| date      | UTC time when the result was generated in the format: "2020-05-02T15:29:30.67Z" |

## Error handling

All exception are handled by a subclass of [ResponseEntityExceptionHandler](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html). Only validation errors are handled explicitly in the application.

### 400 error code - validations

Validation errors are handled with a 400 response code and the following response body:

```json
{
  "timestamp": "2020-04-29T22:24:15.404Z",
  "status": 400,
  "errors": [
    "textFormat must match \"(HTML|TEXT)\"",
    "deliveryType must match \"(PRIORITY_SYNC|PRIORITY_ASYNC|QUEUE)\""
  ]
}
```

### 500 error code - something went wrong

An example of the output produced by an error 500 return code.

```json
{
  "timestamp": "2020-04-30T11:23:03.146Z",
  "status": 500,
  "error": "Internal Server Error",
  "message": "Mail server connection failed; nested exception is com.sun.mail.util.MailConnectException: Couldn't connect to host, port: localhost, 26; timeout -1;\n  nested exception is:\n\tjava.net.ConnectException: Connection refused: connect. Failed messages: com.sun.mail.util.MailConnectException: Couldn't connect to host, port: localhost, 26; timeout -1;\n  nested exception is:\n\tjava.net.ConnectException: Connection refused: connect",
  "trace": "org.springframework.mail.MailSendException: Mail server connection failed; nested exception is com.sun.mail.util.MailConnectException: Couldn't connect to host, port: localhost, 26; timeout -1;\n  nested exception is:\n\tjava.net.ConnectException: Connection refused: connect. Failed messages: com.sun.mail.util.MailConnectException: Couldn't connect to host, port: localhost, 26; timeout -1;\n  nested exception is:\n\t... 95 more\r\n",
  "path": "/mail/send"
}
```

# SMTP Relaying

The application binds a SMTP server to the specified local port. Reads incoming messages and relays them to the specified remote SMTP Server. This means we can send emails from cron or similar small utilities that do not support smtp server sending. The server asks for no authentication, so it should be placed in a secured environment. 

Message is relayed 'as it is', with the only addition of a _"Received"_ header and a _"Message-ID"_ header:

```
Received: from 0:0:0:0:0:0:0:1 (EHLO [IPv6:::1]) ([0:0:0:0:0:0:0:1])
          by LOCALHOSTNAME (Spring Boot g-mail-relayer SMTP Server) with ESMTP ID -88888888
          for <somebody@example.com>;
          Tue, 31 Mar 2020 13:09:24 +0200 (CEST)
Message-ID: <234234234qsdafs3$asfa3asd@example.com>
```

This means that the original `from` address of the email sent must be allowed in the relaying server to be the sender of the message with the authentication provided in the configuration. This is, provided username and password must be authorized to send emails on behalf the original `from` address.

This is true if the configuration selected for that email does not override the `from` address (`overrideFrom` property). In that case, the authorized sender will be the one indicated in the `overrideFromAddress` property.

## Sending method selection headers

As with the API, the email can be classified to match one of the defined relaying methods. An explanation of the matching algorithm is ahead, but the data needed for this classification in the SMTP Relaying sending method is:

+ Original 'from' address
+ `X-GMR-APPLICATION-ID` and `X-GMR-MESSAGE-TYPE` headers

# Configuration

## application.properties

The following properties are supported in the `application.properties` file.

| Property                    | Value  | Description                                                  |
| --------------------------- | ------ | ------------------------------------------------------------ |
| server.servlet.context-path | String | Context path where the web application will be available     |
| server.port                 | Number | Port for http listening. Secure http not supported.          |
| relayer.smtp.server.port    | Number | Listening port for the local smtp server                     |
| spring.cache.jcache.config  | String | Location of the echcache definition file. Can't guess why you would want to change this. |

## json config

The application needs a json config file (in UTF-8 format) to set the configuration for the relaying servers and to get the information to select which one of the listed servers is selected. The json object has four main properties:

| Property     | Description                                                  |
| ------------ | ------------------------------------------------------------ |
| api-default  | Sets the configuration for the server that will send all the emails received via API that cannot be matched with any other configuration. |
| smtp-default | Sets the configuration for the server that will send all the emails received via SMTP that cannot be matched with any other configuration. |
| api          | Sets all the configurations for all the servers that can be matched to an email received via API. |
| smtp         | Sets all the configurations for all the servers that can be matched to an email received via SMTP. |

The objects `api-default`and `smtp-default`share the following properties:

| Property            | Value   | Description                                                  |
| ------------------- | ------- | ------------------------------------------------------------ |
| overrideFrom        | Boolean | When this is set to `true` the 'from' address of the received email is changed for the one specified in `overrideFromAddress` |
| overrideFromAddress | String  | If `overrideFrom` is set to `true`, the email will be changed to set this address in the `from`field of the message. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. |
| authType            | String  | Authentication type of the remote SMTP server the email will be relayed to. Currently only `USERPASS` (LOGIN method) and `NTLM` are supported. |
| username            | String  | Username to authenticate in the relaying server. Both username and password are mandatory. If you want to use a SMTP server with no auth required just use something like `"null"`. |
| password            | String  | Password to authenticate in the relaying server.             |
| domain              | String  | If `authType`is set to `NTLM`, then this field must be set and is the NTLM domain to authenticate the user. |
| host                | String  | IP or host address of the relaying server.                   |
| port                | Number  | Port to which connect on the relaying server.                |
| starttls            | Boolean | Set to `true` if STARTTLS must be used in the relaying server. |

`api`and `smtp`are both an array of objects. This objects have the same properties as `api-default`and `smtp-default` plus these ones, that specify the relaying server selection rule. The selection method and further rules for configuration specification are explained below:

| Property         | Value  | Description                                                  |
| ---------------- | ------ | ------------------------------------------------------------ |
| forFrom          | String | When the relaying server has to be chosen by the `from`address of the message, this should be set to the email address that has to be matched in order to pick this configuration. |
| forApplicationId | String | If you want to pick a sending configuration different for each application that is sending emails, this field sets a custom ID that identifies this configuration. Can't be set at the same time as `forFrom`. |
| forMessageType   | String | If you want to pick a sending configuration different for each application that is sending emails, but the same application has to use different addresses, this field sets a custom message type that identifies this configuration. Can't be set if `forApplicationId` is not set. |

There are configuration examples [here](https://github.com/betler/g-mail-relayer/tree/master/src/main/resources/samples/config).

## Server matching algorithm

First of all, from address, application id and message type are retrieved:

- For api, from the post body arguments
- For smtp, the from address and the X-GMR-APPLICATION-ID and X-GMR-MESSAGE-TYPE headers.

With this data the following server matching algorithm is performed:

1. Application ID takes precedence over from address so if this id is not null, the message type is retrieved from the message.
2. If both message application id AND message type match any of the server configuration, this one is selected.
3. If only application id matches a given configuration, this could happen because:
   - Email message type is null: then a configuration with an application id but no message type is searched. If found, it is selected
   - Email message type is not null but there is not a matching configuration. Then, a configuration with the same application ID AND a null message type is searched. This is: it will never be matched if application id is the same AND not the message type AND the configuration message type is not null.
4. If application id and message type did not give a match, the message from address is searched in the configuration. For this matching, the personal information is removed. This means, `John Doe <john.doe@example.com>`is the same address as `john.doe@example.com`.
5. If none of the steps below did give a match, then the default configuration is used for the applicable sending type (api or smtp).

## I'm confused

Yeah, i know. I prepared this list of examples. I'm not sure if I had understood the previous chapter without having programmed it myself.

Consider the following set of configurations. Not considering if api or smtp because the selection works the same:

| Conf #  | forFrom          | forApplicationId | forMessageType      |
| ------- | ---------------- | ---------------- | ------------------- |
| default | N/A              | N/A              | N/A                 |
| 1       | from@example.com | null             | null                |
| 2       | null             | 'APP1'           | null                |
| 3       | null             | 'APP1'           | 'New Customer'      |
| 4       | null             | 'APP2'           | 'Password Recovery' |

In the next table, it is stated the chosen configuration when different emails are received:

| from                | applicationId | messageType         | Chosen config                                                |
| ------------------- | ------------- | ------------------- | ------------------------------------------------------------ |
| from@example.com    | null          | null                | #1 - from address is used to select the configuration as applicationId is null |
| from@example.com    | 'APP1'        | null                | #2 - the from address exists but applicationId takes precedence. No messageType so rule number 2 matches with null message type. |
| from@example.com    | 'APP1'        | 'Newsletter'        | #2 - message type does not exist in configuration so a null message type for the given application id is selected |
| from@example.com    | 'APP1'        | 'New Customer'      | #3 - application id and message type match                   |
| another@example.com | 'APP3'        | 'Password Recovery' | default - none match, so default is selected                 |
| another@example.com | 'APP2'        | 'New Customer'      | default - application id matches but not message type. As there is not 'APP2' application with a null message type, default is selected. |

## Still confused...

Sorry, don't know what else I can do. You can check the algorithm in the [ConfigFileHelper.java file](https://github.com/betler/g-mail-relayer/blob/master/src/main/java/pro/cvitae/gmailrelayer/config/ConfigFileHelper.java).

## Configuration file rules

This matching algorithm means that for each group of configurations (api or smtp) the  following rules must be met:

1. The configuration can be selected by application or from address, not both, so an error will be raised if `forFrom` and `forApplicationId` are both not null.
2. A message type is a subclass of application so an error will be raised if `forMessageType` is set and `forApplicationId` is null.
3. Each configuration must be unique so:
   - Can't set two identical `forFrom`addresses
   - Can't set two identical `forApplicationId` and `forMessageType` pair values. Keep in mind that `null` is a valid value for the message type. So you can have an application id with no message type and the same application with a specific message type.
