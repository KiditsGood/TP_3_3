<template>
    <div>
        <AppHeader />
        <div class="main">
            <div class="main__flex">
                <div class="main__products">
                    <ProductCard v-for="product in products" :product="product" :key="product.id"/>
                </div>
                <FilterSort />
            </div>
        </div>

    </div>
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    import ProductCard from "@/components/ProductCard";
    import FilterSort from "@/components/FilterSort";
    import {API} from "@/axios";
    export default {
        name: "CatalogVegetables",
        components: {FilterSort, ProductCard, AppHeader},

        data() {
            return {
                products: [],
            }
        },

        methods: {
            async fetchProducts() {
                const prodResp = await API.get('products/all')

                this.products = prodResp.data
            }
        },

        mounted() {
            this.fetchProducts()
        }
    }
</script>

<style lang="scss" scoped>
    .main__products{
        display: flex;
        align-items: flex-start;
        gap: 50px;
        flex-wrap: wrap;
        max-width: 1200px;
        margin-bottom: 100px;
    }
</style>