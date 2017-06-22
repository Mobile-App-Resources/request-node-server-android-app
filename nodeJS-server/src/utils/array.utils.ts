/**
 * Created by maraujo on 3/17/17.
 */

export class ArrayUtils {
    constructor() {
    }

    static getById<T>(id:number, array:Array<T>) {
        if(!array) {
            return null;
        }
        let result = array.filter((obj: any) => { return obj.id == id; });

        return result && result.length > 0 ? result[0] : null;
    }

    static getIds(dataSource: Array<any>): Array<number> {

        if (!dataSource) {
            return null;
        }
        return dataSource.map((cur) => {
            return cur.id;
        });
    }
}