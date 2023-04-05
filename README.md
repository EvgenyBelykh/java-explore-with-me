Ссылка на пулл-реквест:
https://github.com/EvgenyBelykh/java-explore-with-me/pull/4

# explore-with-me
Template repository for Explore-with-me project.
![diagram](https://github.com/EvgenyBelykh/java-explore-with-me/blob/main_svc/explore-with-me-2.png)

## Code from dbdiagram.io:

  ``` 
Project exploreWithMe {
database_type: 'PostgreSql'
}

Table category {
id bigint [pk, increment]
name varchar(64)
}

Table user {
id bigint [pk, increment]
email email
name varchar(64)
}

Table event {
id bigint [pk, increment]
title varchar(120)
annotation varchar(2000)
description varchar(7000)
category_id bigint
confirmed_requests bigint
created_on timestamp
event_date timestamp
initiator_id bigint
location_id id
paid boolean
participant_limit int
published_on timestamp
request_moderation boolean
state int
}

Table state {
id int [pk, increment]
state State
}

enum State{
  PENDING
  PUBLISHED
  CANCELED 
}

Table location {
id bigint
lat float
lon float
}

Table participation_requests {
id bigint [pk, increment]
event_id bigint
created timestamp
requester_id bigint
status Status
}

enum Status{
  PENDING
  APPROVED
  CANCELED
  REJECTED
}

Table compilation {
id bigint [pk, increment]
title varchar(64)
pinned boolean
}

Table compilation_event {
compilation_id bigint pk
event_id bigint pk
}

Table comment {
id bigint [pk, increment]
text varchar(1000)
event_id bigint
author_id bigint
created timestamp
}


REF: event.category_id > category.id
REF: event.initiator_id - user.id
REF: event.location_id > location.id
REF: event.state > state.id
REF: participation_requests.requester_id - user.id
REF: participation_requests.event_id - event.id
REF: compilation.id <> compilation_event.compilation_id
REF: compilation_event.event_id <> event.id
REF: comment.event_id > event.id
REF: comment.author_id > user.id
   ```
