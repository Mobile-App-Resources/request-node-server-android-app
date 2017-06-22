/**
 * Created by rodrigoarantes1 on 5/20/16.
 */

import { Serialize } from "cerialize/dist/serialize";

export class Outcome<T> {


    private result: T;
    private results: Array<T>;

    private resultCount: number;
    
    constructor(queryResult: any = null, type: { new(result: any): T ;} = null) {

        if (type && queryResult) {

            // handle arrays
            if (queryResult.constructor === Array) {
                let tempArray: Array<T> = [];
                for (var i = 0; i < queryResult.length; i++) {
                    var newObject = new type(queryResult[i]);
                    tempArray.push(newObject);
                }
                
                this.results = tempArray;
                this.resultCount = tempArray.length;
                
            } else { // handle single object
                this.result = new type(queryResult);
                this.resultCount = 1;
            }
        }
    }

    // GETTERS
    //////////
    hasResult(): boolean {
        return !this.hasNoResult();
    }

    hasNoResult(): boolean {
        return !this.resultCount; // || this.resultCount <= 0;
    }

    size(): number {
        return this.resultCount;
    }

    getResult(): T {
        return this.result;
    }

    getResults(): Array<T> {
        return this.results;
    }

    getSerializedResults() {
        if(this.hasResult()) {
            return this.result ? Serialize(this.result) : Serialize(this.results);
        } else {
            return null
        }
    }

    // STATIC HELPERS
    /////////////////
    static ResultSuccess<T>(result: T) {
        let outcome = new Outcome<T>(null);
        outcome.resultCount = 1;
        outcome.result = result;
        return outcome;
    }

    static ResultsSuccess<T>(results: Array<T>) {
        let outcome = new Outcome<T>(null);
        outcome.resultCount = results ? results.length : 0;
        outcome.results = results;
        return outcome;
    }

    static Error<E>(): Outcome<E> {
        return new Outcome<E>();
    }
}