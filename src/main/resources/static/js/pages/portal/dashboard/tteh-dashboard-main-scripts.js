$(document).ready(function () {
    const chartSets = {
        daily: {
            labels: ["Oct 20", "Oct 21", "Oct 22", "Oct 23", "Oct 24", "Oct 25", "Oct 26"],
            values: [4000, 4800, 4800, 5600, 6700, 6700, 8900],
            date: "Oct 20, 2026 – Oct 26, 2026"
        },
        weekly: {
            labels: ["Week 1", "Week 2", "Week 3", "Week 4"],
            values: [18500, 22400, 19700, 28600],
            date: "October 1, 2026 – October 26, 2026"
        },
        monthly: {
            labels: ["May", "Jun", "Jul", "Aug", "Sep", "Oct"],
            values: [42000, 51000, 47500, 62000, 71000, 84500],
            date: "May 2026 – October 2026"
        }
    };

    function renderChart(period) {
        const data = chartSets[period || "daily"];
        if (!data) return;
        const max = Math.max(...data.values) * 1.12;
        const bars = data.values.map(function(value) {
            const height = Math.max(3, (value / max) * 100);
            return `<div class="bar-col"><div class="bar" title="₱${value.toLocaleString()}" style="height: ${height}%"></div></div>`;
        }).join("");
        const labels = data.labels.map(function(label) {
            return $("<span>").text(label).prop("outerHTML");
        }).join("");
        $("#bars").html(bars);
        $("#barLabels").html(labels);
        $("#chartDate").text(data.date);
    }

    renderChart("daily");

    $(document).on("click", "[data-period]", function() {
        const $button = $(this);
        $("[data-period]").removeClass("active");
        $button.addClass("active");
        renderChart($button.data("period"));
    });
});