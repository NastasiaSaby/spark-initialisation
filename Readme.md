Le projet *git scala-spark-exemple* montre plusieurs exemples d’utilisation Spark.

Il a été démarré avec SBT.

Il y a des mains qui executent du code DataFrame (*sparkinit.dataframe*) et Dataset (*spark.init.dataset*). 

Il y a des exemples de tests avec *ScalaTest* (*FunSuite*, d’autres variantes sont possibles) avec un trait qui wrappe une *SparkSession* (*SparkSessionTestWrapper*) locale.

Il y a un exemple de *window function* dans *DefineDuplicatesTest*.

Le fichier *log4j.properties* présent dans les ressources de test sert à paramétrer les logs Spark dans les tests.

Le fichier *Generators* se base sur *ScalaCheck* pour générer la *case classe Patrimoine*.

Avec le fichier *application.conf* présent dans les resources du main, le package *sparkinit.configuration* montre un exemple de configuration avec *PureConfig*.
