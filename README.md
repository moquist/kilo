# kilo
====
![kilo](http://www.navy.mil/navydata/communications/flags/kilo.gif)

[kilo. I wish to communicate with you.](http://www.navy.mil/navydata/nav_legacy.asp?id=273)


A Clojure library designed to provide a RESTful interface to the VLACS data stored in a Moodle database.

## Usage

### Config

1. Run `dev-set-up.sh` -- This script creates a ".kilo" directory in your home directory and 
   populates it with a db config file and a web config file.

2. Edit the config files per your environment. Set the db config file so that it has the info 
   needed to connect to a Moodle test database. For my local env, I use ssh and port forwarding
   so that the `//localhost:5433/moodletesttdolan_vlacs_org` URL is actually hitting the `moodletesttdolan_vlacs_org`
   database on the VLACS test server.

### Run

```
lein run -p ~/.kilo
```

Or, in development, fire up a REPL (either with lein or otherwise) and enter:
`(reset)` at the REPL prompt. This will load all .clj files into the REPL and start a jetty instance running the kilo code.


### Test

To confim, use curl, or other http request tool to submit requests to `http://localhost:4001/kilo/user/1`.
Depending on the `Accept` header in the request, the response will be in either JSON or EDN formats.
(Currently, if `Accept` header value is not specified to be `application/json` or `application/edn`, the response
defaults to JSON.)

For example:

```
curl --header "Accept:application/edn" http://localhost:4001/kilo/user/34457 
```
Results in:

```
{:primary_sis_user_idstr nil, :can_masquerade 0, :timemodified 1376064004, :sis_user_id 1234, :timecreated 1324068264, :istest 0, :privilege "GUARDIAN", :sis_user_idstr "1234", :username "guser", :email "somebody@somewhere.com", :firstname "Genie", :lastname "User", :id 12312, :password "cf23df2207d99a74fbe169e3eba035e633b65d94"}
```

And:

```
curl --header "Accept:application/json" http://localhost:4001/kilo/user/34457
```

Results in:

```
{"primary_sis_user_idstr":null,"can_masquerade":0,"timemodified":1376064004,"sis_user_id":1234,"timecreated":1324068264,"istest":0,"privilege":"GUARDIAN","sis_user_idstr":"1234","username":"guser","email":"somebody@somewhere.com","firstname":"Genie","lastname":"User","id":12312,"password":"cf23df2207d99a74fbe169e3eba035e633b65d94"}
```

Currently, data being sent back is the raw response from the database query. Ultimate response format to be determined.

## License

Copyright © 2014 VLACS http://vlacs.org

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
