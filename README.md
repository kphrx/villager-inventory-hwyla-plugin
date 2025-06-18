# Fabric Template Mod

<dl>
  <dt>Mod Name</dt>
  <dd>TemplateMod</dd>
  <dt>Mod ID</dt>
  <dd><code>template-mod</code></dd>
  <dt>Package Name</dt>
  <dd><code>dev.kpherox.template_mod</code></dd>
  <dt>Advanced Options</dt>
  <dd><ul>
    <li>Kotlin Programming Language</li>
    <li>Mojang Mappings</li>
    <li>Data Generation</li>
    <li>Split client and common sources</li>
  </ul></dd>
</dl>

## Initialize project

```zsh
git mv src/client/kotlin/{TemplateMod,MyMod}Client.kt
git mv src/client/kotlin/{TemplateMod,MyMod}DataGenerator.kt
git mv src/client/resources/{template-mod,my-mod}.client.mixins.json

git mv src/main/kotlin/{TemplateMod,MyMod}.kt
git mv src/main/resources/assets/{template-mod,my-mod}/icon.png
git mv src/main/resources/{template-mod,my-mod}.mixins.json

sed -i 's/TemplateMod/MyMod/g' $(git grep -l TemplateMod)
sed -i 's/template-mod/my-mod/g' $(git grep -l template-mod)
sed -i 's/template_mod/my_mod/g' $(git grep -l template_mod)
```
