#!/bin/bash

# add this script to a new directory /utils in uzo-web-app
# scheduler runs ./utils/update_dev_db.sh

curl -s https://s3.amazonaws.com/assets.heroku.com/heroku-client/heroku-client.tgz | tar xz
PATH="/app/heroku-client/bin:$PATH"
heroku pg:copy MAUVE AQUA --app uzo-web-app --confirm uzo-web-app
