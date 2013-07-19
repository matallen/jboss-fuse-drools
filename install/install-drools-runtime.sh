#!/bin/bash

# This script installs the necessary libraries from the drools runtime into maven

URL="file:///home/mallen/.m2/repository"

FILES=$(find . -name "*BRMS*.jar")
echo "Going to install the following files"
for filename in $FILES
do
  echo "$filename"
done

for filename in $FILES
do
  brmsVersion=$(echo $filename | grep -oE "([0-9]{1}\.)*BRMS")
  artifactId=$(echo $filename | grep -oE ".*/(.*)-")
echo "mvn deploy:deploy-file -Dfile=$filename -DgroupId=org.drools -DartifactId=${artifactId%?} -Dversion=$brmsVersion -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true -Durl=$URL"
  mvn deploy:deploy-file -Dfile=$filename -DgroupId=org.drools -DartifactId=${artifactId%?} -Dversion=$brmsVersion -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true -Durl=$URL
done


ECJ=*ecj*
for filename in $ECJ
do
  ecjVersion=$(echo $filename | grep -oE "([0-9]{1})\.([0-9]{1})\.([0-9]{1})")
  mvn deploy:deploy-file -Dfile=$filename -DgroupId=org.eclipse.jdt.core.compiler -DartifactId=ecj -Dversion=$ecjVersion -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true -Durl=$URL
done

MVEL=*mvel2*
for filename in $MVEL
do
  mvelVersion=$(echo $filename | grep -oE "([0-9]{1})\.([0-9]{1})\.([0-9]{1}).([A-Za-z])*([0-9])*")
  echo $mvelVersion
  mvn deploy:deploy-file -Dfile=$filename -DgroupId=org.mvel -DartifactId=mvel2 -Dversion=$mvelVersion -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true -Durl=$URL
done

ANTLR=*antlr-runtime*
for filename in $ANTLR
do 
  antlrVersion=$(echo $filename | grep -oE "([0-9]{1})\.([0-9]{1})")
  mvn deploy:deploy-file -Dfile=$filename -DgroupId=org.antlr -DartifactId=antlr-runtime -Dversion=$antlrVersion -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true -Durl=$URL
done

JXL=*jxl*
for filename in $JXL
do 
  jxlVersion=$(echo $filename | grep -oE "([0-9]{1})\.([0-9]{1})\.([0-9]{1})")
  mvn deploy:deploy-file -Dfile=$filename -DgroupId=jxl -DartifactId=jxl -Dversion=$jxlVersion -Dpackaging=jar -DgeneratePom=true -DcreateChecksum=true -Durl=$URL
done
