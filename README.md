![GitHub](https://img.shields.io/github/license/betler/g-mail-relayer?style=flat-square)
![In development](https://img.shields.io/badge/status-current_development-green?style=flat-square)
[![CodeFactor](https://www.codefactor.io/repository/github/betler/g-mail-relayer/badge?style=flat-square)](https://www.codefactor.io/repository/github/betler/g-mail-relayer)

# g-mail-relayer

This project develops a Java mail relayer. The need comes due to some limitations found in my projects. For sure, there are projects there who can perform this kind of actions, but found none exactly matching and I felt like building it myself. I need to talk to old NTLM servers and not all applications can. Also, sending a lot of emails synchronously in my applications is slow, so an async sending method was desirable.

The goal is to build:
 + A Spring Boot REST API application able to relay emails coming from another application, asynchronously, so the sender can forget about waiting to say EHLO! to a external server.
 + It should include a vanilla SMTP server to cover legacy code that cannot change to call to an API or third-party applications (as an example of my needs, a replacement for MultiSMTP OTRS plugin).

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
    + ¿Body of the message? Still thinking about it, maybe gzipped in B64 to avoid space wasting
    + Optional information storing none/statistics/all
4. Support for NTLM authentication
5. Search/statistics methods.
    + Able to search
        + Received in a period of time
        + By message status / type
    + Able to give information about
        + Number of received emails
        + Number of OK messages
        + Number of pending messages
        + Number of NOK messages
        + Number of retrying messages
6. Able to send email through a REST API - No auth intented to implement here, just ip filtering
7. Able to relay email through a standard 25-smtp port
8. Able to retry email sending for a specified number of times and specified amount of time.
9. Support for callbacks in message sent or message NOK events
10. Separate logs for each application
11. Plugins for beforeSend and afterSend events

## Current status
I'm trying to keep this up to date, but no release is still out, so this refers to the master branch:

| #  | Desc                                        | Status                                                                                                                 |
|----|---------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| 1  | Send emails                                 | ![done](https://img.shields.io/badge/requisite-done!-green) |
| 2  | Defer email sending                         | ![pending](https://img.shields.io/badge/requisite-pending-red)                                                         |
| 3  | Store information about the email           | ![pending](https://img.shields.io/badge/requisite-pending-red)                                                         |
| 4  | Support for NTLM authentication             | ![done](https://img.shields.io/badge/requisite-done!-green) |
| 5  | Search/statistics methods                   | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive)                              |
| 6  | REST API                                    | ![done](https://img.shields.io/badge/requisite-done!-green)                                                            |
| 7  | Standard 25-smtp port                       | ![done](https://img.shields.io/badge/requisite-done!-green)                                                            |
| 8  | Retry email sending                         | ![pending](https://img.shields.io/badge/requisite-pending-red)                                                         |
| 9  | Support for callbacks                       | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive)                              |
| 10 | Separate logs for each application          | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive)                              |
| 11 | Plugins for beforeSend and afterSend events | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive)                              |

# Usage

## REST API Sending

Expanded documentation with max sizes, required object, etc. can be found in:
* [Swagger.io](https://app.swaggerhub.com/apis/c-vitae.pro/g-mail-relayer-api/1.0.0#/models)
* [api.json file](https://github.com/betler/g-mail-relayer/blob/master/src/main/resources/api/api.json)
* [Locally generated HTML doc](https://github.com/betler/g-mail-relayer/blob/master/src/main/resources/api/api-doc.html)

Examples of api calls can be found in [samples directory](https://github.com/betler/g-mail-relayer/tree/master/src/main/resources/api/samples).

The only existing method is `POST` `/mail/send`.

Here are listed the fields of the json objects the api requires.

### EmailMessage
This is the json object representing an email message that is going to be sent:

| Property       | Description                              |
|----------------|------------------------------------------|
| applicationId  | Optional ID to identify the calling application with two objectives. Separate sending configuration can be placed in function of this parameter and it is (will be) used in statistics. No specific format required. Example: `APP1`|
| messageType | Optional message type identification. This property can't be set without `applicationId` and refers to a specific mail type for a specific app. Separate sending configuration can be placed in function of this parameter and it is (will be) used in statistics. No specific format required. Example: `Password recovery`. |
| from | Email address specifiying the sender of the message. If no `applicationId` is set, this field could be used to select the sending configuration. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. Keep in mind that this address, although mandatory, can be overriden before sending/relaying the email. See sending configuration chapter for more info.|
| replyTo | Optional field to set the 'reply to' address. This field is not overriden in any case.  |
| to | Array of destination addresses. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. |
| cc | Optional array of cc addresses. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. |
| bcc | Optional array of bcc addresses. Can be set with or without brackets as in `Aunt Doe <aunt.doe@example.com>` or just `aunt.doe@example.com`. |
| subject | Subject of the message. Maximum length is 255, not only because that is the max lenght of a subject in MS Outlook, but because... hey, who wants to read an email with such a long subject, anyway? Not me. |
| body | Body of the message. Can be text or html. Format must be specified with the `textFormat` field, and encoding must be specified with the `textEncoding` field. Max length is 50.000, but still thinking if it should be greater. |
| textFormat | Specifies if the body is text or HTML. Possible values are `TEXT` and `HTML`. |
| textEncoding | Encoding of the message subject, body and addresses. Almost any field in this object may be affected by this setting. |
| priority | Sets the priority of the message. Values 1 to 5, from the highest[1] to lowest[5]. This value is set in an `X-Priority` header. No other headers like `X-MSMail-Priority` or `Importance` are set. Anyway, if you need any of these, they can be set in the `headers` field. |
| notBefore | Optionally delay message delivery until the time specified, as defined by [date-time - RFC3339](http://xml2rfc.ietf.org/public/rfc/html/rfc3339.html#anchor14 "date-time - RFC3339"). It is not guaranteed that the email will be sent exactly at this time, but at the first scheduled delivery time after this time. This option is ignored if `deliveryType` is set to other than `QUEUE`. Example: '2015-03-17T10:30:45Z' |
| deliveryType | Sets the delivery type: <ul><li>`PRIORITY_SYNC` makes a synchronized inmediate sending of the message. The API does not return until the message is delivered (or tried to).</li><li>`PRIORITY_ASYNC` makes an inmediate background sending. The API returns the ID of the message with `QUEUED` status but the message is sent inmediately in the background.</li><li>`QUEUE` queues the message until the next scheduled batch processing of queued mails'</li></ul> |
| attachments | Array of attachments. Object is explained below. |
| headers | Array of headers to added to the message. Object is explained below. |

### Attachment
This is the json object representing an attachment included in the message.

| Property       | Description                              |
|----------------|------------------------------------------|
| cid            | Optional CID identificator for inline attachements. Setting a CID will force the attachment to be inlined. If you do not want the attachment to be inlined, do not set the CID field. Alphanumeric characters and `._@-$&()[]` are allowed |
| filename | Filename of the attachment. Alphanumeric characters, space and `_-$&()[]` are allowed. |
| contentType | Optional content type. |
| content | The file contents in Base64 representation. Current max size is 6MB. |

### Header
This is the json object representing a header included in the message.

| Property       | Description                              |
|----------------|------------------------------------------|
| name | Name of the header. |
| value | Value of the header. |

This one wasn't tough, was it?

### ErrorDetail

***<span style='color:red;'>PENDING</span>***

### SendEmailResult

***<span style='color:red;'>PENDING</span>***

***<span style='color:red;'>PENDIENTES DE REVISAR</span>***
 - [ ] List item
 - [ ] Buscar todos los adress > address

replyTo puede llevar brackets???

## SMTP Relaying

The application binds a SMTP server to the specified local port. Reads incoming messages and relays them to the specified remote SMTP Server. This means we can send emails from cron 
or similar small utilities that do not support smtp server sending (just localhost and non-authenticated sending). 

Message is relayed 'as it is', with the only addition of a _"Received"_ header and a _"Message-ID"_ header:

```
Received: from 0:0:0:0:0:0:0:1 (EHLO [IPv6:::1]) ([0:0:0:0:0:0:0:1])
          by LOCALHOSTNAME (Spring Boot g-mail-relayer SMTP Server) with ESMTP ID -88888888
          for <somebody@example.com>;
          Tue, 31 Mar 2020 13:09:24 +0200 (CEST)
Message-ID: <234234234qsdafs3$asfa3asd@example.com>
```

This means that the original `from` address of the original email must be allowed in the relaying server to be the sender of the message. This is, provided username and password must be authorized to send emails on behalf the original `from` address.

This is true if `relayer.smtp.relay.from.override` is set to `false`. If overriding is activated, the `from` address is replaced by the value of `relayer.smtp.relay.auth.username` property. In this case, this property must be a full email address.

## application.properties

### relayer.smtp.server.xxx properties

These properties apply to the local smtp server.

| Property       | Value       | Description                              |
|----------------|-------------|------------------------------------------|
| .port          | Number      | Listening port for the local smtp server |

### relayer.smtp.relay.xxx properties

These properties apply to the relaying of the incoming messages.

| Property          | Value                    | Description                                                                                                                                  |
|-------------------|--------------------------|----------------------------------------------------------------------------------------------------------------------------------------------|
| .overrideFrom     | Boolean                  | If true, 'from' address will be replaced by the 'relayer.smtp.auth.username property'. This means this property must be a full email address |
| .auth.type        | _USERPASS_ &vert; _NTLM_ | NTML for NTLM authentication; USERPASS for the rest                                                                                          |
| .auth.username    | String                   | Username for the authentication in the relaying server. Must be a full email address if 'relayer.smtp.from.override' is set to true          |
| .auth.password    | String                   | Password for the authentication in the relaying server                                                                                       |
| .auth.domain      | String                   | Domain for NTLM authentication. Only needed if 'relayer.smtp.auth.type' is set to 'NTLM'                                                     |  
| .server.starttls  | Boolean                  | Enable STARTTLS in communication with relaying stmp server                                                                                   |
| .server.host      | String                   | Address or hostname of the relaying smtp server                                                                                              |
| .server.port      | Number                   | Port of the relaying smtp server                                                                                                             |

