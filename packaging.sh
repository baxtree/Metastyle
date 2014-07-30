if grails uninstall-plugin release; then
  if grails uninstall-plugin svn; then
    if grails install-plugin release 1.0.0; then
      if grails clean; then
        if grails compile; then
          grails prod war
        fi
      fi
    fi
  fi
fi
