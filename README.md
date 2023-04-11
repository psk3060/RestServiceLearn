# 1. Simple REST 서비스 생성

<ul>
	<li>url : https://spring.io/guides/gs/rest-service</li>
	<li>version : openjdk17</li>
	<li>TestClass : GreetingTest(mvnw clean test -Dtest="GreetingTest")</li>
</ul>


# 2. REST 방식으로 Apache Geode(In Memory Store)에 접근

<ul>
	<li>url : https://spring.io/guides/gs/accessing-gemfire-data-rest</li>
	<li>version : openjdk17</li>
	<li>Github : https://github.com/spring-guides/gs-accessing-gemfire-data-rest</li>
	<li>Geode 문서 : https://geode.apache.org/docs/guide/114/rest_apps/develop_rest_apps.html</li>
	<li>TestClass : GeodeTest(mvnw clean test -Dtest="GeodeTest")</li>
</ul>

# 3. MongoDB Data REST

<ul>
	<li>url : https://spring.io/guides/gs/accessing-mongodb-data-rest</li>
	<li>version : openjdk17</li>
	<li>Github : https://github.com/spring-guides/gs-accessing-mongodb-data-rest</li>
	<li>TestClass : MongoDBTest(mvnw clean test -Dtest="MongoDBTest")</li>
</ul>

# 4. MariaDB + JPA Data REST

<ul>
	<li>url : https://spring.io/guides/gs/accessing-data-mysql</li>
	<li>version : openjdk17</li>
	<li>Github : https://github.com/spring-guides/gs-accessing-data-mysql</li>
	<li>TestClass : MariaDBTest(mvnw clean test -Dtest="MariaDBTest")</li>
</ul>

# 5. Neo4j Data REST

<ul>
	<li>url : https://spring.io/guides/gs/accessing-neo4j-data-rest</li>
	<li>version : openjdk17</li>
	<li>Github : https://github.com/spring-guides/gs-accessing-neo4j-data-rest</li>
	<li>neo4j : community version</li>
	<li>TestClass : Neo4jTest(mvnw clean test -Dtest="Neo4jTest")</li>	
</ul>

# 6. Consuming REST(RestService END). Status 처리는 계속 진행

<ul>
	<li>url : https://spring.io/guides/gs/consuming-rest</li>
	<li>version : openjdk17</li>
	<li>Github : https://github.com/spring-guides/gs-consuming-rest</li>
	<li>Github-Special : https://github.com/spring-guides/quoters</li>	
	<li>TestClass : RestComsumingTest(mvnw clean test -Dtest="RestComsumingTest")</li>		
	
</ul>


## 기타 

<ol type="2">
	<li>jar 파일 생성(build) : mvnw clean package</li>
	<li>로컬 서버 실행 : mvnw spring-boot:run</li>
	<li>애노테이션 설멍
		<ul>
			<li>@SpringBootApplication : @Configuration, @EnableAutoConfiguration, @ComponentScan</li>
			<li>@ClientCacheApplication : Apache Geode(또는 GemFire) 기반 애플리케이션용 Spring Data가 ClientCache 인스턴스를 생성하여 Apache Geode(또는 GemFire) 캐시 클라이언트로 사용</li>
			<li>@EnableEntityDefinedRegions : 애플리케이션 영구 엔티티를 기반으로 Pivotal Geode(또는 GemFire)의 리전을 생성할 수 있도록 @Configuration(@SpringBootApplication) 애노테이션을 작성한 클래스에 추가하는 애노테이션</li>
			<li>@EnableGemfireRepositories : Gemfire 저장소 활성화</li>
			<li>200 제외 Status 처리(예외)</li>
			<li>@JsonIgnoreProperties : 해당 유형에 해당되지 않을 경우 변환 시 해당 값은 무시</li>
			<li>@JsonProperty : JSON 속성값 직접 지정(변수명은 다르지만 JSON으로 변환을 하고자 할 경우)</li>
		</ul>
	</li>	
</ol>
