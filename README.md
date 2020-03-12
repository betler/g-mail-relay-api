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

## Current status

| #  | Desc                                        | Status                                                                         |
|----|---------------------------------------------|--------------------------------------------------------------------------------|
| 1  | Send emails                                 | ![pending](https://img.shields.io/badge/requisite-pending-red)                 |
| 2  | Defer email sending                         | ![pending](https://img.shields.io/badge/requisite-pending-red)                 |
| 3  | Store information about the email           | ![pending](https://img.shields.io/badge/requisite-pending-red)                 |
| 4  | Support for NTLM authentication             | ![pending](https://img.shields.io/badge/requisite-pending-red)                 |
| 5  | Search/statistics methods                   | ![pending](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 6  | REST API                                    | ![pending](https://img.shields.io/badge/requisite-pending-red)                 |
| 7  | Standard 25-smtp port                       | ![pending](https://img.shields.io/badge/requisite-pending-red)                 |
| 8  | Retry email sending                         | ![pending](https://img.shields.io/badge/requisite-pending-red)                 |
| 9  | Support for callbacks                       | ![pending](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 10 | Separate logs for each application          | ![pending](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
| 11 | Plugins for beforeSend and afterSend events | ![pending](https://img.shields.io/badge/requisite-future_enhancement-inactive) |
