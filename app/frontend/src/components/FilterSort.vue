<template>
    <div class="main__search">
        <input type="text" maxlength="20" class="main__search-input" placeholder="Введите название..."/>
        <button type="button" class="main__search-button">Поиск</button>
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
            <select class="main__search-select">
                <option>По возрастанию</option>
                <option>По убыванию</option>
            </select>
        </div>
        <button type="button" class="main__search-button--sort">Сортировка</button>
    </div>
</template>

<script>
    export default {
        name: "FilterSort"
    }
</script>

<style lang="scss" scoped>
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