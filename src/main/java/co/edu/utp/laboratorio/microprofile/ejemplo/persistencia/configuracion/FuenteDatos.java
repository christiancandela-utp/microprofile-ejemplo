package co.edu.utp.laboratorio.microprofile.ejemplo.persistencia.configuracion;

import javax.annotation.sql.DataSourceDefinition;

@DataSourceDefinition(
        name="java:app/ejemplo/mysql",
        className = "com.mysql.cj.jdbc.MysqlDataSource",
        serverName = "localhost",
        portNumber = 3306,
        user= "root",
        password = "12345678",
        databaseName = "ejemplo",
        initialPoolSize = 2,
        minPoolSize = 2,
        maxPoolSize = 10,
        properties = {
                "useSSL=false",
                "useInformationSchema=true",
                "nullCatalogMeansCurrent=true",
                "nullNamePatternMatchesAll=false"
        }
)
public class FuenteDatos {
}
