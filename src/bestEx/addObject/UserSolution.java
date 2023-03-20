package bestEx.addObject;

import java.util.ArrayList;

class UserSolution {
    public final int MAX_GROUP = 11001;
    public int MAP_SIZE;
    public Group[] groups;
    int maxGroupID;
     
    void init(int N) {
        groups = new Group[MAX_GROUP];
        for (int i = 0; i < MAX_GROUP; i++) {
            groups[i] = new Group();
        }
         
        MAP_SIZE = N - 1;
        maxGroupID = 0;
    }
 
    void addObject(int mID, int y1, int x1, int y2, int x2) {
        maxGroupID = mID;
        groups[mID].update(y1, x1, x2, y2);
    }
 
    int group(int mID, int y1, int x1, int y2, int x2) {
        Group group = groups[mID];
        group.update(y1, x1, x2, y2);
        int result = 0;
        for (int i = mID - 1; i > 0; i--) {
            if (!groups[i].isActive) continue;
            if (!group.contain(groups[i])) continue;
            result += i;
            group.child.add(groups[i]);
        }
         
        //If there is only 1 child in group
        if (group.child.size() <= 1) {
            group.isActive = false;
            return 0;
        }
         
        //Update group position
        maxGroupID = mID;
        x1 = y1 = MAP_SIZE;
        x2 = y2 = 0;
        for (Group child: group.child) {
            child.isActive = false;
            y1 = Math.min(y1, child.top);
            x1 = Math.min(x1, child.left);
            y2 = Math.max(y2, child.bottom);
            x2 = Math.max(x2, child.right);
        }
         
        group.update(y1, x1, x2, y2);
        return result;
    }
 
    int ungroup(int y1, int x1) {
        Group group = selectGroup(x1, y1);
        if (group == null) return 0;
        if (group.child.isEmpty()) return 1;
        group.isActive = false;
        return ungroup(group);
    }
 
    int moveObject(int y1, int x1, int y2, int x2) {
        Group group = selectGroup(x1, y1);
        if (group == null) return -1;
        y2 = Math.min(y2, MAP_SIZE + group.top - group.bottom);
        x2 = Math.min(x2, MAP_SIZE + group.left - group.right);
        int deltaX = x2 - group.left;
        int deltaY = y2 - group.top;
         
        group.offsetX += deltaX;
        group.offsetY += deltaY;
         
        group.update(y2, x2, group.right + deltaX, group.bottom + deltaY);
         
        //System.out.println(group);
        return y2 * 10000 + x2;
    }
     
    public int ungroup(Group group) {
        if (group.child.isEmpty()) {
            group.isActive = true;
            return 1;
        }
         
        int result = 0;
         
        for(Group child: group.child) {
            child.update(child.top + group.offsetY, child.left + group.offsetX, child.right + group.offsetX, child.bottom + group.offsetY);
            child.offsetX += group.offsetX;
            child.offsetY += group.offsetY;
            result += ungroup(child);
        }
         
        return result;
    }
     
    public Group selectGroup(int x, int y) {
        for (int i = maxGroupID; i > 0; i--) {
            if (!groups[i].isActive) continue;
            if (groups[i].contain(y, x)) return groups[i];
        }
        return null;
    }
     
    public class Group {
        int top, left, right, bottom;
        int offsetX, offsetY;
        ArrayList<Group> child = new ArrayList<Group>();
        boolean isActive = true;
         
        public void update(int top, int left, int right, int bottom) {
            this.top = top;
            this.left = left;
            this.right = right;
            this.bottom = bottom;
        }
         
        public boolean contain(Group group) {
            if (top <= group.top && left <= group.left && right >= group.right &&  bottom >= group.bottom) {
                return true;
            }
            return false;
        }
         
        public boolean contain(int y, int x) {
            if (top <= y && left <= x && right >= x &&  bottom >= y) {
                return true;
            }
            return false;
        }
         
        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return "group top = "+top+", left = "+left +", right = "+right+", bottom = "+bottom +", child size = "+child.size()+", isActive = "+isActive;
        }
    }
 
}
