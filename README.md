![In development](https://img.shields.io/badge/status-current_development-green)

# g-mail-relayer

This project intends to build a Java mail relayer. The need comes due to some limitations found in my projects. For sure, there are projects there who can perform this kind of actions, but found none exactly matching and I felt like building it myself.

The goal is to build:
 + A Spring Boot REST application able to relay emails coming from another application, asynchronously, so the sender can forget about waiting to say EHLO!
 + It should include a vanilla SMTP server to cover legacy code that cannot for any reason (ie, money: come on guys, for things like this IT should get more budget) be adapted to the REST API


Thanks to [@avthart]( https://github.com/avthart ) and his [spring-boot-james-smtp-server]( https://github.com/avthart/spring-boot-james-smtp-server ) project as it enlightened me.

# Requirements

1. Able to send:
    1. HTML or text emails
    2. Attachments (attached or inline)
    3. Specific headers
2. Defer email sending to a specific time
3. Store information about the email
    + Reception time
    + Application sending the email, so it can support different applications
    + Message type (application dependant so the application can search about specific type of mails)
    + Status
    + Last sending time (last try to send message)
    + Number of retries
    + to/cc/bc
    + ¿Body of the message? Still thinking about it, maybe gzipped in B64 to avoid space wasting
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

## Functionality: Current status

| #  | Desc                                        | Status                                                                                                                 |
|----|---------------------------------------------|------------------------------------------------------------------------------------------------------------------------|
| 1  | Send emails                                 | ![partially implemented](https://img.shields.io/badge/requisite-partially_implemented-yellow) - Local SMTP Server only |
| 2  | Defer email sending                         | ![pending](https://img.shields.io/badge/requisite-pending-red)                                                         |
| 3  | Store information about the email           | ![pending](https://img.shields.io/badge/requisite-pending-red)                                                         |
| 4  | Support for NTLM authentication             | ![partially implemented](https://img.shields.io/badge/requisite-partially_implemented-yellow) - Local SMTP Server only |
| 5  | Search/statistics methods                   | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive)                              |
| 6  | REST API                                    | ![pending](https://img.shields.io/badge/requisite-pending-red)                                                         |
| 7  | Standard 25-smtp port                       | ![done](https://img.shields.io/badge/requisite-done!-green)                                                            |
| 8  | Retry email sending                         | ![pending](https://img.shields.io/badge/requisite-pending-red)                                                         |
| 9  | Support for callbacks                       | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive)                              |
| 10 | Separate logs for each application          | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive)                              |
| 11 | Plugins for beforeSend and afterSend events | ![future_enhancement](https://img.shields.io/badge/requisite-future_enhancement-inactive)                              |

## Send email through REST API: Current status

| Property         | Status                                                                                                                 |
|------------------|------------------------------------------------------------------------------------------------------------------------|
| From             | |
| ReplyTo          |
| To               |
| CC               |
| BCC              |
| Subject          |
| Body             |
| TextFormat       |
| TextEncoding     |
| Priority         |
| NotBefore        |
| Attachments      |
| Headers          |

# Usage

## REST API Sending

Not currently supported

## SMTP Relaying

The application binds a SMTP server to the specified local port. Reads incoming messages and relays them to the specified remote SMTP Server. This means we can send emails from cron 
or similar small utilities that do not support smtp server sending (just localhost and non-authenticated sending). 

Message is relayed 'as it is', with the only addition of a _"Received"_ header, something like:

```
Received: from 0:0:0:0:0:0:0:1 (EHLO [IPv6:::1]) ([0:0:0:0:0:0:0:1])
          by LOCALHOSTNAME (Spring Boot g-mail-relayer SMTP Server) with ESMTP ID -88888888
          for <somebody@example.com>;
          Tue, 31 Mar 2020 13:09:24 +0200 (CEST)
```

This means, the original `from` address of the original email must be allowed in the relaying server to be the sender of the message. This is, provided username and password must be authorized to send emails on behalf the original `from` address.

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
