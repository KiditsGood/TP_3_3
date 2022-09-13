<template>
    <div>
        <AppHeader />
        <div class="main">
            <div class="main__flex">
                <div class="main__products">
                    <ProductCard v-if="product.category == 'Овощи'" v-for="product in searchAndSortHandler" :product="product" :key="product.id"/>
                </div>
                <div class="main__search">
                    <input v-model="searchQuery" type="text" maxlength="20" class="main__search-input" placeholder="Введите название..."/>
                    <div class="main__search-flex">
                        <p class="main__search-filter">Фильтрация по цене</p>
                        <div class="filter__inputs">
                            <input type="number" maxlength="4" class="filter__inputs-input"/>
                            <input type="number" maxlength="4" class="filter__inputs-input"/>
                        </div>
                    </div>
                    <button type="button" class="main__search-button--filter">Фильтрация</button>
                    <div class="main__search-flex">
                        <p class="main__search-filter">Сортировка по цене</p>
                        <select v-model="sortValue" class="main__search-select">
                            <option disabled selected value="">Выберите из списка</option>
                            <option>По возрастанию</option>
                            <option>По убыванию</option>
                        </select>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    import ProductCard from "@/components/ProductCard";
    import {API} from "@/axios";
    export default {
        name: "CatalogVegetables",
        components: {ProductCard, AppHeader},

        data() {
            return {
                products: [],
                searchQuery: '',
                sortValue: ''
            }
        },

        methods: {
            async fetchProducts() {
                const prodResp = await API.get('products/all')

                this.products = prodResp.data
            },

        },

        mounted() {
            this.fetchProducts()
        },

        computed: {
            sortedPosts() {
                return [...this.products].sort((product1, product2) => this.sortValue === 'По возрастанию' ? product1.price - product2.price : this.sortValue === 'По убыванию' ? product2.price - product1.price : this.products)
            },

            searchAndSortHandler() {
                return this.sortedPosts.filter(product => product.name.toLowerCase().includes(this.searchQuery.toLowerCase()))
            }
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
    .main__search{
        border: 3px solid #009900;
        box-shadow: 0 0 0 1px lightgray;
        width: 250px;
        border-radius: 50px;
        display: flex;
        flex-direction: column;
        gap: 30px;
        padding: 25px 20px;

        &-input, .filter__inputs-input{
            border: 2px solid #009900;
            border-radius: 20px;
            padding: 5px 10px;
            font-size: 16px;
            font-weight: 500;
            line-height: 16px;

            &::placeholder{
                color: lightgray;
            }
        }

        &-button, &-button--sort, &-button--filter{
            font-size: 16px;
            line-height: 16px;
            font-weight: 600;
            color: white;
            background: #009900;
            transition: 0.2s;
            border-radius: 15px;
            padding: 15px 30px;

            &:hover{
                color: #009900;
                outline: 1px solid #009900;
                background: none;
                cursor: pointer;
            }
        }

        &-flex{
            display: flex;
            flex-direction: column;
            gap: 15px;
        }

        &-filter{
            font-size: 14px;
            line-height: 14px;
            font-weight: 500;
            color: black;
        }

        &-select{
            border: 2px solid #009900;
            border-radius: 20px;
            padding: 5px 10px;
            font-size: 16px;
            font-weight: 500;
            line-height: 16px;
        }
    }
    .filter__inputs{
        display: flex;
        align-items: center;
        justify-content: space-around;
        gap: 10px;

        &-input{
            width: 80px;
        }
    }
</style>