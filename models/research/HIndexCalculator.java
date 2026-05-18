package OOP_dreamTeam.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class HIndexCalculator {
    private HIndexCalculator() {
    }

    public static int calculate(List<ResearchPaper> papers) {
        if (papers == null || papers.isEmpty()) {
            return 0;
        }

        List<Integer> citations = new ArrayList<>();
        for (ResearchPaper paper : papers) {
            if (paper != null) {
                citations.add(paper.getCitations());
            }
        }

        citations.sort(Collections.reverseOrder());

        int hIndex = 0;
        for (int i = 0; i < citations.size(); i++) {
            int rank = i + 1;
            if (citations.get(i) >= rank) {
                hIndex = rank;
            } else {
                break;
            }
        }
        return hIndex;
    }
}
