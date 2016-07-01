#!/bin/bash 

function Work()
{
	branch=`git branch -v | awk '{if($1=="*") print $2;}'`
	gitpushcmd=`echo $branch | awk '{printf("git push origin %s", $1);}'`
	
	echo $gitpushcmd
	$gitpushcmd
	echo""
}

function main()
{
	workpath=`pwd`
	
	Work ;

	#cd ../../../apps	
	#Work ;	

	cd $workpath
}

main
