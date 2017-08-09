
init = function WS(){
    function CacheStatistics() {

        document.getElementById('status').innerHTML = 'Status OK';

        this.ws = null;
        this.interval = null;
        this.init = function () {
            this.ws = new WebSocket("ws://localhost:8080/admin_by_ws");
            this.ws.onopen = function (event) {

            };

            this.ws.onmessage = function (event) {
                var data = JSON.parse(event.data);

                document.getElementById('queries').innerHTML = data.queries;
                document.getElementById('queryCacheHitCount').innerHTML = data.queryCacheHitCount;
                document.getElementById('queryCacheMissCount').innerHTML = data.queryCacheMissCount;
                document.getElementById('queryCachePutCount').innerHTML = data.queryCachePutCount;
                document.getElementById('secondLevelCacheHitCount').innerHTML = data.secondLevelCacheHitCount;
                document.getElementById('secondLevelCacheMissCount').innerHTML = data.secondLevelCacheMissCount;
                document.getElementById('secondLevelCachePutCount').innerHTML = data.secondLevelCachePutCount;
                document.getElementById('secondLevelCacheRegionNames').innerHTML = data.secondLevelCacheRegionNames;
                document.getElementById('sessionOpenCount').innerHTML = data.sessionOpenCount;
                document.getElementById('sessionCloseCount').innerHTML = data.sessionCloseCount;
                document.getElementById('secondLevelHitU').innerHTML = data.secondLevelHitU;
                document.getElementById('secondLevelMissU').innerHTML = data.secondLevelMissU;
                document.getElementById('secondLevelPutCountU').innerHTML = data.secondLevelPutCountU;
                document.getElementById('secondLevelSizeU').innerHTML = data.secondLevelSizeU;
                document.getElementById('secondLevelHitP').innerHTML = data.secondLevelHitP;
                document.getElementById('secondLevelMissP').innerHTML = data.secondLevelMissP;
                document.getElementById('secondLevelPutCountP').innerHTML = data.secondLevelPutCountP;
                document.getElementById('secondLevelSizeP').innerHTML = data.secondLevelSizeP;
                document.getElementById('secondLevelHitA').innerHTML = data.secondLevelHitA;
                document.getElementById('secondLevelMissA').innerHTML = data.secondLevelMissA;
                document.getElementById('secondLevelPutCountA').innerHTML = data.secondLevelPutCountA;
                document.getElementById('secondLevelSizeP').innerHTML = data.secondLevelSizeP;
                document.getElementById('secondLevelSizeA').innerHTML = data.secondLevelSizeA;

            };

            this.ws.onclose = function (event) {
                document.getElementById('status').innerHTML = 'Status websocket CLOSED';
            };
        };
    }
    var cache = new CacheStatistics();
    cache.init();
};