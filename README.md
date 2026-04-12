<!-- modrinth_exclude.start -->
# Villager Inventory Plugin
[![Modrinth Version][Modrinth-Badge-Version]][Modrinth-Page]
[![Modrinth Game Versions][Modrinth-Badge-Game]][Modrinth-Page]

Show villager's inventory slots to Hwyla's tooltip. Supported Jade or WTHIT.
<!-- modrinth_exclude.end -->

## Requirements
- Jade or WTHIT
- A Kotlin library. (needs to load `org.jetbrains.kotlin:kotlin-stdlib`)
  - Fabric: **Fabric Language Kotlin** ([GitHub][fabric-language-kotlin-GitHub], [Modrinth][fabric-language-kotlin-Modrinth], [CurseForge][fabric-language-kotlin-CurseForge])
    - (with Jade) This mod's entrypoint provider is required.
  - NeoForge:
    - **Kotlin for Forge**: [GitHub][KotlinForForge-GitHub], [Modrinth][KotlinForForge-Modrinth], [CurseForge][KotlinForForge-CurseForge]
    - **KotlinLangForge**: [GitHub][KotlinLangForge-GitHub], [Modrinth][KotlinLangForge-Modrinth]

## Example
| Mob      | Jade                                         | WTHIT                                          |
|---------:|----------------------------------------------|------------------------------------------------|
| Villager | ![Villager's tooltip in Jade][Villager-Jade] | ![Villager's tooltip in WTHIT][Villager-WTHIT] |
|    Allay | ![Allay's tooltip in Jade][Allay-Jade]       | ![Allay's tooltip in WTHIT][Allay-WTHIT]       |
|   Piglin | ![Piglin's tooltip in Jade][Piglin-Jade]     | ![Piglin's tooltip in WTHIT][Piglin-WTHIT]     |

[Modrinth-Page]: https://modrinth.com/mod/villager-inventory-hwyla-plugin
[Modrinth-Badge-Version]: https://img.shields.io/modrinth/v/ESa9RivE?style=flat-square
[Modrinth-Badge-Game]: https://img.shields.io/modrinth/game-versions/ESa9RivE?style=flat-square

[fabric-language-kotlin-GitHub]: https://github.com/FabricMC/fabric-language-kotlin
[fabric-language-kotlin-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/fabric-language-kotlin
[fabric-language-kotlin-Modrinth]: https://modrinth.com/mod/fabric-language-kotlin
[KotlinForForge-GitHub]: https://github.com/thedarkcolour/KotlinForForge
[KotlinForForge-CurseForge]: https://www.curseforge.com/minecraft/mc-mods/kotlin-for-forge
[KotlinForForge-Modrinth]: https://modrinth.com/mod/kotlin-for-forge
[KotlinLangForge-GitHub]: https://github.com/btwonion/KotlinLangForge
[KotlinLangForge-Modrinth]: https://modrinth.com/mod/kotlin-lang-forge

[Villager-Jade]: https://cdn.modrinth.com/data/ESa9RivE/images/ea127410a4c913adb12de3104699331b622ae604.png
[Allay-Jade]: https://cdn.modrinth.com/data/ESa9RivE/images/7a91a6720b21355b44afe5ad56e0800403755981.png
[Piglin-Jade]: https://cdn.modrinth.com/data/ESa9RivE/images/1dff1fc6d77026ea48501fdacc7adfcb5ac4ee7a.png
[Villager-WTHIT]: https://cdn.modrinth.com/data/ESa9RivE/images/70f7e4e789e620cbdbe0359bb6d95bc0ce207b3e.png
[Allay-WTHIT]: https://cdn.modrinth.com/data/ESa9RivE/images/592b29986716a35b3d124c6ab99461af7cc6b629.png
[Piglin-WTHIT]: https://cdn.modrinth.com/data/ESa9RivE/images/769826344a028eb76f56cb381a9e277cdf587c2f.png
