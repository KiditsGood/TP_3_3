<template>
    <AppHeader />
</template>

<script>
    import AppHeader from "@/components/AppHeader";
    import axios from 'axios'
    export default {
        name: "AppMain",
        components: {AppHeader},
        data() {
            return {

            }
        },
        methods: {
            async checkUser() {
                const response = await axios.get('users/get_user')

                console.log(response)
            }
        }
    }
</script>

<style scoped>

</style>