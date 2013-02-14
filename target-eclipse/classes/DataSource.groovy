dataSource {
	pooled = true
    driverClassName = "com.mysql.jdbc.Driver"
	username = "root"
	password = "123456"
}

//dataSource {
//	pooled = true
//	driverClassName = "org.postgresql.Driver"
//	username = "s0896253"
//	password = "68737937"
//	sslmode = "disable"
//}

hibernate {
	show_sql = true
	cache.use_second_level_cache = true
	cache.use_query_cache = true
	cache.provider_class = 'net.sf.ehcache.hibernate.EhCacheProvider'
}

// environment specific settings

environments {
	development {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/cssmetaselector?useUnicode=yes&characterEncoding=UTF-8"
		}
	}
	test {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/cssmetaselector?useUnicode=yes&characterEncoding=UTF-8"
		}
	}
	production {
		dataSource {
			dbCreate = "update" // one of 'create', 'create-drop','update'
			url = "jdbc:mysql://localhost:3306/cssmetaselector?useUnicode=yes&characterEncoding=UTF-8"
		}
	}
}

//environments {
//	development {
//		dataSource {
//			dbCreate = "create" // one of 'create', 'create-drop','update'
//			url = "jdbc:postgresql://pgresearch.inf.ed.ac.uk/s0896253"
////			url = "jdbc:postgresql://okbook.inf.ed.ac.uk:8188/cssmetaselector"
////			url = "jdbc:postgresql://localhost:5432/cssmetaselector"
//		}
//	}
//	test {
//		dataSource {
//			dbCreate = "create" // one of 'create', 'create-drop','update'
//			url = "jdbc:postgresql://pgresearch.inf.ed.ac.uk/s0896253"
////			url = "jdbc:postgresql://okbook.inf.ed.ac.uk:8188/cssmetaselector"
////			url = "jdbc:postgresql://localhost:5432/cssmetaselector"
//		}
//	}
//	production {
//		dataSource {
//			dbCreate = "create" // one of 'create', 'create-drop','update'
//			url = "jdbc:postgresql://pgresearch.inf.ed.ac.uk/s0896253"
////			url = "jdbc:postgresql://okbook.inf.ed.ac.uk:8188/cssmetaselector"
////			url = "jdbc:postgresql://localhost:5432/cssmetaselector"
//		}
//	}
//}
