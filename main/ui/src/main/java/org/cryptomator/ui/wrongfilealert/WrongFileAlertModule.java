package org.cryptomator.ui.wrongfilealert;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.cryptomator.ui.common.DefaultSceneFactory;
import org.cryptomator.ui.common.FXMLLoaderFactory;
import org.cryptomator.ui.common.FxController;
import org.cryptomator.ui.common.FxControllerKey;
import org.cryptomator.ui.common.FxmlFile;
import org.cryptomator.ui.common.FxmlScene;

import javax.inject.Named;
import javax.inject.Provider;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

@Module
abstract class WrongFileAlertModule {

	@Provides
	@WrongFileAlertWindow
	@WrongFileAlertScoped
	static FXMLLoaderFactory provideFxmlLoaderFactory(Map<Class<? extends FxController>, Provider<FxController>> factories, DefaultSceneFactory sceneFactory, ResourceBundle resourceBundle) {
		return new FXMLLoaderFactory(factories, sceneFactory, resourceBundle);
	}

	@Provides
	@WrongFileAlertWindow
	@WrongFileAlertScoped
	static Stage provideStage(ResourceBundle resourceBundle, @Named("windowIcon") Optional<Image> windowIcon) {
		Stage stage = new Stage();
		stage.setTitle(resourceBundle.getString("wrongFileAlert.title"));
		stage.setResizable(false);
		stage.initModality(Modality.WINDOW_MODAL);
		windowIcon.ifPresent(stage.getIcons()::add);
		return stage;
	}

	@Provides
	@FxmlScene(FxmlFile.WRONGFILEALERT)
	@WrongFileAlertScoped
	static Scene provideWrongFileAlertScene(@WrongFileAlertWindow FXMLLoaderFactory fxmlLoaders) {
		return fxmlLoaders.createScene("/fxml/wrongfilealert.fxml");
	}

	// ------------------

	@Binds
	@IntoMap
	@FxControllerKey(WrongFileAlertController.class)
	abstract FxController bindWrongFileAlertController(WrongFileAlertController controller);
}
