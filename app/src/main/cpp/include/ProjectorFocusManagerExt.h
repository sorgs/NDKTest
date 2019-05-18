#ifndef __PROJECTORFOCUSMANAGER_EXT_H__
#define __PROJECTORFOCUSMANAGER_EXT_H__

#include "ProjectorFocusWrapperType.h"

class ProjectorFocusManagerWrapper;
class ProjectorFocusManagerExt {
public:
    ProjectorFocusManagerExt();
    ~ProjectorFocusManagerExt();
    void autoFocus(uint8_t dir);
    CameraBufInfo openCamera();
    uint32_t readCamera();
    uint8_t closeCamera(CameraBufInfo info);
};

#endif