<template>
    <div class="wraps">
        <!-- 左上 -->
        <div class="upperleft">
            左上
        </div>

        <!-- 右上 -->
        <div class="upperRight">
            右上
        </div>

        <!-- 左下 -->
        <div class="lowerLeft">
            左下
        </div>

        <!-- 右下 -->
        <div class="lowerRight">
            右下
        </div>

    </div>
</template>

<script setup name="Index">

</script>

<style scoped>
.wraps {
    position: relative;
    background-size: cover;
    width: 100%;
    height: calc(100vh - 85px);
    overflow: hidden; /* 防止 leftpart 超出 wrap */
}

/* 左上 */
.upperleft {
    position: absolute;
    top: 1%; /* 距离上面 6% */
    bottom: 49%; /* 距离下面 60% */
    left: 0.5%; /* 距离左侧1% */
    width: 49%;
    border: 1px solid #D9EDF7;
}

/* 左下 */
.lowerLeft {
    position: absolute;
    top: 52%; /* 距离上面 43% */
    bottom: 1%; /* 距离下面 50% */
    left: 0.5%; /* 距离左侧1% */
    width: 49%;
    border: 1px solid #D9EDF7;
}

/* 右上 */
.upperRight {
    position: absolute;
    top: 1%; /* 距离上面 6% */
    bottom: 49%; /* 距离下面 60% */
    left: 50.5%; /* 距离左侧1% */
    width: 49%;
    border: 1px solid #D9EDF7;
}

/* 右下 */
.lowerRight {
    position: absolute;
    top: 52%; /* 距离上面 43% */
    bottom: 1%; /* 距离下面 50% */
    left: 50.5%; /* 距离左侧1% */
    width: 49%;
    border: 1px solid #D9EDF7;
}
</style>


