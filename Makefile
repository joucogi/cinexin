.PHONY: build
build:
	@./gradlew build  --warning-mode all

start_backoffice:
	@./gradlew bootRun --args='backoffice'  --warning-mode all

start_visualizer:
	@./gradlew bootRun --args='visualizer'  --warning-mode all

test_unit:
	@./gradlew :backoffice:test --warning-mode all
	@./gradlew :visualizer:test --warning-mode all

test_feature:
	@./gradlew featureTest -PsubprojectArg=backoffice
	@./gradlew featureTest -PsubprojectArg=visualizer