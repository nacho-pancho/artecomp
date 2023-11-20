REPO=`svn info | awk '/URL:/ { print $2 }'`
echo "Repositorio: $REPO"
rm -rf dist
mkdir dist
cd dist
svn export ${REPO} 
cd artecomp
./build.sh
cd ..
rm -f artecomp.zip
zip -r9 artecomp.zip artecomp
