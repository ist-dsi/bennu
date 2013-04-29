package pt.ist.bennu.core.rest.json;

import java.util.Locale;

import pt.ist.bennu.core.annotation.DefaultJsonAdapter;
import pt.ist.bennu.core.i18n.I18N;
import pt.ist.bennu.core.security.UserSession;
import pt.ist.bennu.core.util.ConfigurationManager;
import pt.ist.bennu.json.JsonBuilder;
import pt.ist.bennu.json.JsonViewer;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@DefaultJsonAdapter(UserSession.class)
public class UserSessionViewer implements JsonViewer<UserSession> {
    @Override
    public JsonElement view(UserSession user, JsonBuilder ctx) {
        JsonObject json;
        if (user != null) {
            json = ctx.view(user.getUser()).getAsJsonObject();
            json.add("groups", ctx.view(user.accessibleGroups()));
        } else {
            json = new JsonObject();
        }
        final Locale currentLocale = I18N.getLocale();
        json.add("locale", ctx.view(currentLocale));
        json.add("locales", ctx.view(ConfigurationManager.getSupportedLocales()));
        return json;
    }
}