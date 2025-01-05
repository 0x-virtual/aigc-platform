import keyBy from 'lodash/keyBy';
import {getTaskDataAnalysis, getTaskDataAnalysisToday} from "@/api/data-analysis";
import dayjs from "dayjs";

// export async function getTodayTaskDataAnalysis(taskCategory: string) {
//     const {data} = await getTaskDataAnalysis({
//         task_category: taskCategory,
//         start_at: dayjs()
//             .startOf("day")
//             .toDate().getTime(),
//         end_at: dayjs()
//             .endOf("day")
//             .toDate().getTime(),
//     });
//     return data;
// }

export async function getTodayTaskDataAnalysis() {
    const {data} = await getTaskDataAnalysisToday({
    });
    return data;
}

export async function convertLangua1geDisplay(lang: string) {
    const {data} = await getTaskDataAnalysis({
        task_category: "qa",
        start_at: dayjs()
            .startOf("day")
            .toDate().getTime(),
        end_at: dayjs()
            .endOf("day")
            .toDate().getTime(),
    });
}
