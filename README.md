# 1. Simple REST 서비스 생성

<ul>
	<li>url : https://spring.io/guides/gs/rest-service</li>
	<li>version : openjdk17</li>
</ul>


# 2. REST 방식으로 Apache Geode(In Memory Store)에 접근

<ul>
	<li>url : https://spring.io/guides/gs/accessing-gemfire-data-rest</li>
	<li>version : openjdk17</li>
	<li>Github : https://github.com/spring-guides/gs-accessing-gemfire-data-rest</li>
	<li>Geode 문서 : https://geode.apache.org/docs/guide/114/rest_apps/develop_rest_apps.html</li>
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
		</ul>
	</li>	
</ol>
