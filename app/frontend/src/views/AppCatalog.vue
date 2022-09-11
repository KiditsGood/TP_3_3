<template>
    <div>
        <AppHeader />
        <div class="main">
            <div class="main__catalog">
                <router-link to="/catalog/drinks" class="main__catalog-item">
                    <p class="main__catalog-item--text">Напитки</p>
                    <img src="../assets/static/img/milk.svg" class="main__catalog-item--pic"/>
                </router-link>
                <router-link to="/catalog/meet" class="main__catalog-item">
                    <p class="main__catalog-item--text">Мясо, птица</p>
                    <img src="../assets/static/img/meat.svg" class="main__catalog-item--pic"/>
                </router-link>
                <router-link to="/catalog/bread" class="main__catalog-item">
                    <p class="main__catalog-item--text">Хлеб, выпечка</p>
                    <img src="../assets/static/img/bread.svg" class="main__catalog-item--pic"/>
                </router-link>
                <router-link to="/catalog/vegetables" class="main__catalog-item">
                    <p class="main__catalog-item--text">Овощи</p>
                    <img src="../assets/static/img/vegetables.svg" class="main__catalog-item--pic"/>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    export default {
        name: "AppCatalog",
        components: {AppHeader}
    }
</script>

<style lang="scss" scoped>
    .main__catalog{
        display: flex;
        align-items: center;
        flex-wrap: wrap;
        gap: 70px;

        &-item{
            width: 300px;
            height: 150px;
            display: flex;
            align-items: center;
            justify-content: center;
            gap: 10px;
            border: 3px solid #336633;
            border-radius: 50px;
            transition: 0.2s;

            &--text{
                font-size: 30px;
                line-height: 30px;
                font-weight: 500;
                color: black;
            }

            &--pic{
                width: 30px;
            }

            &:hover{
                transform: translateY(-20px);
                box-shadow: 0 0 10px 0 #336633;
                cursor: pointer;
            }
        }
    }
</style>