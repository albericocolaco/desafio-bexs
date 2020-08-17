# Routes - JOB  
  
## Stacks  
 - Java - V8
 - Maven - V3

## 1 - Build
This step is necessary to generate the jar and download the dependencies.
> Build  `mvn clean install`

## 2 - Run Application
> Start the application `mvn exec:java -Dexec.mainClass="br.com.arcls.RoutesJobApplication" -Dexec.classpathScope=runtime`