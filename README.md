# 0. REST 정리

## 1) RESTful 서비스 구현 시 얻을 수 있는 이점(REST는 표준이 아니라, 단순한 접근방식일 뿐)
<ol>
	<li>이전 버전과 호환되는 API</li>
	<li>진화 가능한 API</li>
	<li>확장 가능한 서비스</li>
	<li>보안 서비스</li>
	<li>상태 저장, 비저장 서비스</li>
</ol>

# 1. Simple REST 서비스 생성

<ul>
	<li>url : https://spring.io/guides/gs/rest-service</li>
	<li>보충2 : https://spring.io/guides/gs/actuator-service</li>
	<li>보충3(Response 헤더에 Cross-Origin Resource Sharing) : https://spring.io/guides/gs/rest-service-cors</li>
	<li>version : openjdk17</li>
	<li>TestClass : GreetingTest(mvnw clean test -Dtest="GreetingTest")</li>
	<li>TestClass : ActuatorTest(mvnw clean test -Dtest="ActuatorTest")</li>
	<li>TestClass : CorsTest(mvnw clean test -Dtest="CorsTest")</li>	
	<li>actuator github : https://github.com/spring-guides/gs-actuator-service</li>
	<li>cors github : https://github.com/spring-guides/gs-rest-service-cors</li>
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
	<li>neo4j : community version(https://neo4j.com)</li>
	<li>TestClass : Neo4jTest(mvnw clean test -Dtest="Neo4jTest")</li>	
</ul>

# 6. Consuming REST(RestService)

<ul>
	<li>url : https://spring.io/guides/gs/consuming-rest</li>
	<li>version : openjdk17</li>
	<li>Github : https://github.com/spring-guides/gs-consuming-rest</li>
	<li>Github-Special : https://github.com/spring-guides/quoters</li>	
	<li>TestClass : RestComsumingTest(mvnw clean test -Dtest="RestComsumingTest")</li>	
</ul>


## 학습 중 정리

<ul>
	<li>Apache Geode : 광범위하게 분산된 클라우드 아키텍처 전체에서 애플리케이션에 대한 실시간 액세스 제공해주는 데이터 관리 플랫폼(여러 프로세스 간 메모리, CPU, 네트워크 리소스 등을 폴링하여 애플리케이션 개체 및 동작을 관리)</li>
	<li>MongoDB : 도큐먼트(예 : JSON) DB</li>
	<li>Neo4j : 그래프 DB</li>	
	<li>RepresentationModel : 객체를 JSON 정보로 전환해줄 뿐만 아니라, 링크 정보도 제공해줌</li>
	<li>hateoas : REST API 서비스 개발 시 편리함</li>
	<li>REST Tut Github : https://github.com/spring-guides/tut-rest</li>
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
			<li>@JsonIgnoreProperties : 해당 유형에 해당되지 않을 경우 변환 시 해당 값은 무시</li>
			<li>@JsonProperty : JSON 속성값 직접 지정(변수명은 다르지만 JSON으로 변환을 하고자 할 경우)</li>
			<li>@JsonCreator : POJO 인스턴스를 JACKON으로 변경</li>
			<li>@RestControllerAdvice : 컨트롤러에서 발생하는 예외를 전역적으로 처리할 수 있는 기능 지원</li>
			<li>@Aspect : 리턴타입 변환 기능(AOP) 사용하기 위해 필요한 애노테이션</li>
			<li>@EnableAspectJAutoProxy : ASPECT 애노테이션 사용하기 위해 필요한 애노테이션</li>
			<li>@SpringBootTest의 webEnvironment를 이용하여, 테스트 시 포트 충돌 방지</li>
		</ul>
	</li>
	<li>
		<ul>
			<li>ALL - TRACE - DEBUG - INFO - WARN - ERROR - FATAL - OFF 순으로 높아짐</li>
			<li>onMatch, onMismatch : 필터링되었을 때와 되지 않은 로깅 레벨들에 대한 처리(ACCEPT, DENY)</li>
			<li>minLevel : 설정한 레벨 이하의 레벨들을 필터링(DEBUG로 설정하면, TRACE와 DEBUG 필터링)</li>
			<li>maxLevel : 설정한 레벨 이상의 레벨들을 필터링(ERROR와 FATAL 필터링)</li>			
			<li>minLevel과 maxLevel을 함께 : minLevel과 maxLevel 사이의 로그는 필터링</li>
		</ul>
	</li>
	
</ol>

## TODO

<ol type="2">
	<li>애노테이션 설멍
		<ul>
			<li>200 제외 Status 처리(예외) : OK(Enum으로 관리할 필요 있음)</li>
			<li>Controller(기능 테스트용 컨트롤러 제외) Response 중복코드 제거(Advice) => 불가(Controller에서 ResponseDataVo를 반환하고, Advice에서 형변환하여 반환할 경우 ClassCastException 발생</li>
			<li>Logging 정리 : OK</li>
			<li>CORS 설정 : 각 컨트롤러에 설정하는 것보다 별도의 Bean을 추가하는 것이 좋아서 후자를 선택</li>
		</ul>
	</li>	
</ol>
