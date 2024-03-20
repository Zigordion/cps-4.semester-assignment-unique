export function convertTimeToDate(timestamp:string):string{
    //TODO:
    return '2000'
}

export function convertWindDirection(direction: number | undefined): string {
    const directions: [number, number, string][] = [
        [337.6, 22.5, 'N'],
        [22.6, 67.5, 'NV'],
        [67.6, 112.5, 'V'],
        [112.6, 157.5, 'SV'],
        [157.6, 202.5, 'S'],
        [202.6, 247.5, 'SØ'],
        [247.6, 292.5, 'Ø'],
        [292.6, 337.5, 'NØ']
    ];

    if (direction === undefined) {
        console.error('direction is undefined, cannot convert to Symbol');
        return '??';
    }

    for (const [min, max, cardinal] of directions) {
        if (direction >= min && direction <= max) {
            return cardinal;
        }
    }

    console.error('winddirection is not within any range')
    return 'Unknown';
}
