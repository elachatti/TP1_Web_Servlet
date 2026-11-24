# TP1 – Programmation Web Avancée (ING1-GL, ISIMM)

Servlets, cookies, sessions, JSP, JavaBean, MVC, filtres et listeners, avec **Tomcat 11** (API `jakarta.servlet`).

Le projet est déployé **à la main** : le dossier `tp1` est copié tel quel dans `webapps` de Tomcat, et les classes sont compilées avec `javac` (pas de Maven).

## Environnement

| Outil | Version |
| --- | --- |
| JDK | 21 (à adapter à votre version : `java -version`) |
| Apache Tomcat | 11.0.26 |
| OS | Windows 11 |
| Éditeur | Visual Studio Code + Extension Pack for Java |
| Navigateur | Google Chrome |

Dans VS Code, `.vscode/settings.json` ajoute `servlet-api.jar` de Tomcat aux bibliothèques du projet (`java.project.referencedLibraries`), sinon les imports `jakarta.servlet` sont soulignés en rouge. Adapter le chemin si Tomcat est installé ailleurs.

## Arborescence

```
tp1/
├── index.html            menu de toutes les parties
├── form.html             1.1 / 1.2  formulaire POST -> FormServlet
├── items.html            1.3.2      formulaire -> ListItems
├── login.html            1.4.1      formulaire -> LoginServlet
├── form-jsp.html, bean.jsp            2.2  JSP + JavaBean
├── expression.jsp, scriptlet.jsp, declaration.jsp   2.1
├── mvc.html, old.jsp, young.jsp       3    vues MVC
├── META-INF/context.xml  active la persistance des sessions (test de 1.4.3)
└── WEB-INF/
    ├── web.xml           filtres (ordre), listener RequestListener
    └── classes/
        ├── ex1/          servlets, filtres, listeners
        ├── isep/lab1/bean/Person.java
        └── lab/mvc/{model/Person.java, controller/Controller.java}
```

## Compilation (Windows)

Depuis `WEB-INF\classes` :

```bat
cd "C:\Program Files\Apache Software Foundation\Tomcat 11.0\webapps\tp1\WEB-INF\classes"
javac -d . -cp "C:\Program Files\Apache Software Foundation\Tomcat 11.0\lib\servlet-api.jar" ex1\*.java isep\lab1\bean\*.java lab\mvc\model\*.java lab\mvc\controller\*.java
```

Puis **redémarrer Tomcat** (un seul Tomcat à la fois : arrêter le service Windows si on lance `catalina.bat run`).

## URL de test

| Partie | URL |
| --- | --- |
| Menu | http://localhost:8080/tp1/ |
| 1.1 Formulaire | http://localhost:8080/tp1/form.html (servlet : `/FormServlet` et `/Form_Servlet`) |
| 1.2 Cookies | `/ReadCookie`, `/ReadCookie?action=delete`, `/VisitCounter` |
| 1.3 Sessions | `/ShowSession`, `/items.html` → `/ListItems` |
| 2 JSP | `/expression.jsp`, `/scriptlet.jsp`, `/declaration.jsp`, `/form-jsp.html` |
| 3 MVC | `/mvc.html` → `/Controller` → `old.jsp` / `young.jsp` |
| 1.4 Filtres | `/login.html`, `/ListItems` (protégé), `/Logout` |
| 1.4 Listeners | `/LoginServlet?action=update`, `/LoginServlet?action=remove`, `/AttributeTest` |

## Configuration choisie

- **Servlets** : annotations `@WebServlet` (pas de doublon dans `web.xml`).
- **Filtres** : `web.xml` uniquement. `LoggingFilter` (avec `logPrefix`) est mappé avant `SecurityFilter`.
- **Listeners** : `@WebListener`, sauf `RequestListener`, déclaré avec `<listener>` dans `web.xml`.
