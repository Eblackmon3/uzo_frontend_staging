#!/bin/bash

# add this script to a new directory /utils
# scheduler runs ./utils/update_dev_db.sh in uzo-web-app

curl -s https://s3.amazonaws.com/assets.heroku.com/heroku-client/heroku-client.tgz | tar xz
PATH="/app/heroku-client/bin:$PATH"
heroku update
heroku pg:copy MAUVE AQUA --app uzo-web-app --confirm uzo-web-app