> # ***BuyCars***
>
> ## Descripción de la temática
> Se trata de una Web de compra/venta de coches en la que un usuario podrá ver una gran variedad de coches para comprar. En la parte pública de la web, el usuario podrá hacer una búsqueda filtrada para encontrar el vehículo que desee, ver su descripción, características, precio, etc. También tendrá la opción de logarse o registrarse para acceder a la parte privada de la aplicación.
>
> En la parte privada el usuario podrá subir su vehículo para venderlo, negociar con otro usuario o concretar una compra de un vehículo, además de todas las funcionalidad de la parte pública de la web.
>
> ## Nombre y descripcion de cada entidad
> **Coche:** esta entidad nos mostrará todos los datos relacionados con el vehículo, así como: Marca, Modelo, Matricula, Color, etc.<br/>
> **Categoría:** en esta entidad aparecerán las distintas categorías o familias de cada vehículo como por ejemplo: coches familiares, 4x4, americanos, japoneses, etc.<br/>
>**Usuario:** en esta entidad aparecerán los datos relativos a los usuarios registrados en la aplicación como el nombre, apellidos, email, teléfono, etc.<br/>
> **Compra:** en esta entidad guardaremos todas las compras o transacciones realizadas entre usuarios, en la que aparecerán entre otros coche, comprador, vendedor, precio de la compra, etc.<br/>
> **Venta:** en esta entidad guardaremos los datos relacionados a los usuarios y los coches que venden además del precio de venta y más datos.<br/>
> **CocheCategoria:** en esta tabla, creada a consecuencia de una relación N:M entre la tabla Coche y la tabla Categoria, guardaremos los datos de cada coche y su categoría correspondiente. Como un coche puede tener más de una categoría necesitamos guardar estos datos en esta tabla auxiliar, ya que no podríamos meter estos datos en la tabla Coche.<br/>
> 
> ## Integrantes del equipo de desarrollo:
> **Pavel Egido del Arco**<br/>
>   * Email: p.egido@alumnos.urjc.es<br/>
>   * Cuenta GitHub: o0Pavelo0 <br/>
>   
>**Alvaro Rollin Guerro**<br/>
>   * Email:	a.rollin@alumnos.urjc.es<br/>
>   * Cuenta GitHub: ROLLIN89 <br/>
>   
>**Osvaldo Renato	Luzuriaga Luzuriaga**<br/>
>   * Email: or.luzuriaga@alumnos.urjc.es <br/>
>   * Cuenta GitHub: orluzuriaga <br/>
>
> ## Tablero Trello
>  [Tablero BuyCars] (https://trello.com/invite/b/89zXO16r/06b870b346ef37954a5d89b8330a5326/sistemas-distribuidos)
>
> ## Diagrama de navegacion
>  [Diagrama de navegación] ( http://prezi.com/q3d9w2dci5w4/?utm_campaign=share&utm_medium=copy)  
>
>
> ## Diagrama UML
> [Diagrama UML] (https://github.com/o0Pavel0o/SistemasDistribuidos/blob/master/UMLBuyCars.png)
>
>## Diagrama Entidad-Relacion
> [Diagrama Entidad-Relación] (https://github.com/o0Pavel0o/SistemasDistribuidos/blob/master/Entidad_relacion.png )
>
>## Instrucciones azure
1. Creamos un certificado pem para acceder a la mv
2. Creamos una mv en azure
3. Accedemos a la mv utilizando las claves creadas<br/>
>            ssh -i clave privada azureuser@ ip mv
4. Instalamos OpenJDK 8 JRE: <br/>
>            sudo add-apt-repository ppa:openjdk-r/ppa 
>            sudo apt-get update 
>            sudo apt-get install openjdk-8-jre 
 5. Instalamos MySql<br/>
>            sudo apt-get update
>            sudo apt-get install -y mysql-server
 6. Comprobamos que tenemos acceso a MySql:<br/>
>            mysql -u root -p
 7. Subimos el .jar para ello utilizamos la clave privada, escribiremos la siguiente secuencia<br/>
>            scp -i "clave privada azure" BuyCars-0.0.1-SNAPSHOT.jar azureuser@ "ip vm":/home/azureuser/ 
 8. Configuramos la BBDD para ello accedemos como root <br/>
>           mysql -u root -p 
>           mysql> create database buycars; 
>           mysql> create user ‘renato’@’%’ identified by ‘123456’; 
>           mysql> grant all privileges on buycars.* to ‘renato’@’%’ ;
>           mysql> flush privileges;
>           mysql> exit; 
 9. Comprobamos que tenemos acceso a la BBDD <br/>
>           mysql -u renato -p buycars <br/>
10. Ejecutamos la aplicación: 
>           java -jar BuyCars-0.0.1-SNAPSHOT.jar 
11. Accedemos a la aplicación tecleando en la barra de busqueda de tu navegador **https://buycars.cloudapp.net** o **https://buycars2.cloudapp.net/**
>
## Balanceo de carga SSL PassThrough
1. Instalamos haproxy:<br/>
>          sudo apt-get update
>          sudo apt-get install haproxy
2. Configuramos haproxy <br/>
>        sudo vi /etc/haproxy/haproxy.cfg
3. Generamos el certificado
>     sudo mkdir /etc/ssl/xip.io <br/>
>     $ sudo openssl genrsa -out /etc/ssl/xip.io/xip.io.key 1024<br/>
>     $ sudo openssl req -new -key /etc/ssl/xip.io/xip.io.key \ <br/>
>                  -out /etc/ssl/xip.io/xip.io.csr<br/>
>       Country Name (2 letter code) [AU]:US <br/>
>       State or Province Name (full name) [Some-State]:Connecticut<br/>
>       Locality Name (eg, city) []:New Haven<br/>
>       Organization Name (eg, company) [Internet Widgits Pty Ltd]:SFH<br/>
>       Organizational Unit Name (eg, section) []:<br/>
>       Common Name (e.g. server FQDN or YOUR name) []:*.xip.io<br/>
>       Email Address []:<br/>
>
>       Please enter the following 'extra' attributes to be sent with your certificate request<br/>
>       A challenge password []:<br/>
>       An optional company name []:<br/>
>       $ sudo openssl x509 -req -days 365 -in /etc/ssl/xip.io/xip.io.csr \<br/>
>       -signkey /etc/ssl/xip.io/xip.io.key \<br/>
>       -out /etc/ssl/xip.io/xip.io.crt<br/>
4. Generamos el archivo pem
>      $ sudo cat /etc/ssl/xip.io/xip.io.crt /etc/ssl/xip.io/xip.io.key \<br/>
>          | sudo tee /etc/ssl/xip.io/xip.io.pem<br/>
>
5. Configuramos el frondend: conexiones http entrantes
>       frontend www-http<br/>
>       bind haproxy_www_public_IP:80<br/>
>       reqadd X-Forwarded-Proto:\ http<br/>
>       default_backend www-backend<br/>
6. Configuramos la cx https entrantes
>      frontend www-https<br/>
>      bind haproxy_www_public_IP:443 ssl crt /etc/ssl/private/example.com.pem<br/>
>      reqadd X-Forwarded-Proto:\ https<br/>
>      default_backend www-backend<br/>
>
8. Configuramos el backend
>     backend www-backend<br/>
>     mode http<br/>
>     balance roundrobin<br/>
>     option forwardfor<br/>
>     option httpchk HEAD / HTTP/1.1\r\nHost:localhost<br/>
>     server web02 172.17.0.3:9001 check<br/>
>     server web03 172.17.0.3:9002 check<br/>
>     http-request set-header X-Forwarded-Port %[dst_port]<br/>  
>     http-request add-header X-Forwarded-Proto https if { ssl_fc }<br/>
 ## Diagrama Infraestrucdtura Azure
> [Diagrama Infraestrucdtura Azure] 
<img src="https://github.com/o0Pavel0o/SistemasDistribuidos/blob/master/diagrama.jpg" width="888" height="430">

 ## Video funcionamiento aplicación
> https://vimeo.com/218383284

 ## Video infraestrucdtura Azure
 > https://vimeo.com/218476404

