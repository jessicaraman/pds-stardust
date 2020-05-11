#!/bin/bash

function triggerJob() {

    echo
    echo Release version is : $1
    echo Next snapshot will be : $2
    echo

    url="https://pds.stardust:XXXX/job/XXXX/buildWithParameters?RELEASE_VERSION=${releaseVersion}&NEXT_SNAPSHOT=${nextSnapshot}"

    curl -v -X POST -u 'XXX:XXX' $url

}  

continue=false

releaseVersion=$1
nextSnapshot=$2

if ! [[ $releaseVersion =~ ^[0-9]+(\.[0-9]+){1,2}$ ]]; 
then
    echo Release version is invalid !
    exit
fi

if ! [[ $nextSnapshot =~ ^[0-9]+(\.[0-9]+){1,2}$ ]]; 
then
    echo Next snapshot version is invalid !
    exit
fi

nextSnapshot="${nextSnapshot}-SNAPSHOT"

while [ -n "$1" ]; do

	case "$1" in

	-y) continue=true ;;
	-Y) continue=true ;;
	--)
		shift 
		break
		;;
	esac

	shift

done

if [ $continue == "false" ]
then

    read -p "Are you sure you want to continue ? (Y/*)" -n 1 -r
    echo
    if [[ $REPLY =~ ^[Yy]$ ]];
    then
        triggerJob $releaseVersion $nextSnapshot
    fi
else
    triggerJob $releaseVersion $nextSnapshot
fi
